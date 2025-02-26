package ru.astongroup.notifications.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astongroup.notifications.client.UserClient;
import ru.astongroup.notifications.dto.ChangeNotificationStatusDto;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.dto.UserRequestDto;
import ru.astongroup.notifications.entity.EmailDetails;
import ru.astongroup.notifications.entity.Notification;
import ru.astongroup.notifications.entity.NotificationStatus;
import ru.astongroup.notifications.entity.NotificationType;
import ru.astongroup.notifications.exception.NotificationNotFoundException;
import ru.astongroup.notifications.exception.UserNotFoundException;
import ru.astongroup.notifications.mapper.NotificationMapper;
import ru.astongroup.notifications.repository.NotificationRepository;
import ru.astongroup.notifications.util.EmailMessages;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserClient userClient;
    private final EmailService emailService;
    private static final String EMAIL_SUBJECT = "Аренда электромобилей оповещает вас";

    @Override
    public NotificationResponseDto createNotification(NotificationCreateDto dto) {
        log.info("Начинаю отправку уведомления пользователю с id = {}", dto.getUserId());
        UserRequestDto user = userClient.getUserById(dto.getUserId())
                .orElseThrow(() -> {
                    log.warn("Пользователь с id = {} не найден", dto.getUserId());
                    return new UserNotFoundException(String.format("Пользователь с id = %d не найден", dto.getUserId()));
                });
        String message = EmailMessages.createMessage(user, dto.getNotificationType());
        NotificationStatus status = sendEmail(user.getEmail(), message);
        return saveNotification(dto, message, status);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<NotificationResponseDto> getAll() {
        log.info("Пытаюсь получит коллекцию уведомлений");
        Collection<Notification> notifications = notificationRepository.findAll();
        log.info("Коллекция уведомлений получена");
        return notifications.stream().map(NotificationMapper::mapToResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponseDto getDtoById(Long notificationId) {
        Notification notification = getById(notificationId);

        return NotificationMapper.mapToResponseDto(notification);
    }

    // Изменяем статус уведомления
    @Override
    public NotificationResponseDto changeStatus(ChangeNotificationStatusDto dto) {
        log.info("Пытаюсь поменять статус уведомления с id = {}", dto.getNotificationId());
        Notification notification = getById(dto.getNotificationId());
        notification.setStatus(dto.getStatus());
        notificationRepository.save(notification);
        log.info("Изменён статус уведомления с id = {} на {}", dto.getNotificationId(), dto.getStatus());

        return NotificationMapper.mapToResponseDto(notification);
    }

    // Удаляем уведомление по id
    @Override
    public void deleteById(Long notificationId) {
        log.info("Пытаюсь удалить уведомление с id = {}", notificationId);
        Notification notification = getById(notificationId);
        notificationRepository.delete(notification);
        log.info("Удалено уведомление с id = {}", notificationId);
    }

    // Получим коллекцию с нужным статусом. Фильтранём в репозитории через Query
    // Нужно подумать как это можно сделать лучше, нужна ли регистронезависимость
    @Override
    public Collection<NotificationResponseDto> getByStatus(NotificationStatus status) {
        log.info("Пытаюсь получить все записи со статусом =  {}", status);
        Collection<Notification> notifications = notificationRepository.finAllByStatus(status);
        log.info("Получены записи со статусом =  {}", status);

        return notifications.stream().map(NotificationMapper::mapToResponseDto).toList();
    }

    // Упаковываем письмо и отправляем голубя
    @Override
    public NotificationStatus sendEmail(String email, String message) {
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(email)
                .message(message)
                .subject(EMAIL_SUBJECT)
                .build();
        NotificationStatus status = emailService.sendSimpleEmail(emailDetails);
        log.info("Уведомление с текстом {} отправлено на почту {}", message, email);
        return status;
    }

    // Сохраняем отправленное уведомление в БД
    public NotificationResponseDto saveNotification(NotificationCreateDto dto, String message, NotificationStatus status) {
        log.info("Пытаюсь сохранить новое уведомлений для пользователя с id = {}", dto.getUserId());
        Notification notification = notificationRepository.save(NotificationMapper.matToNotification(dto, message, status));
        log.info("Сохранено уведомление для пользователя с id = {} на бронь с id = {}", dto.getUserId(), dto.getBookingId());
        return NotificationMapper.mapToResponseDto(notification);
    }

    // Получаем уведомление по id
    public Notification getById(Long notificationId) {
        log.info("Пытаюсь получить уведомление по id = {}", notificationId);
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> {
                    log.warn("Не удалось получить уведомление с id = {}", notificationId);
                    return new NotificationNotFoundException(String
                            .format("Уведомление с id = %d не найдено", notificationId));
                });
        log.info("Уведомление с id = {} получено", notificationId);

        return notification;
    }
}
