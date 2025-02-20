package ru.astongroup.notifications.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astongroup.notifications.client.UserClient;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.dto.UserRequestDto;
import ru.astongroup.notifications.entity.EmailDetails;
import ru.astongroup.notifications.entity.Notification;
import ru.astongroup.notifications.entity.NotificationStatus;
import ru.astongroup.notifications.entity.NotificationType;
import ru.astongroup.notifications.exception.UserNotFoundException;
import ru.astongroup.notifications.mapper.NotificationMapper;
import ru.astongroup.notifications.repository.NotificationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserClient userClient;
    private final EmailService emailService;
    private static final String EMAIL_SUBJECT = "Аренда электромобилей оповещает вас";

    @Override
    public NotificationResponseDto createNotification(NotificationCreateDto dto) {
        NotificationResponseDto response = NotificationResponseDto.builder().build();
        // нужно разнести текст по константам в другой класс
        // ну и причесать весь код нормально
        switch (dto.getNotificationType()) {
            case NotificationType.NEW_BOOKING -> response = sendNotification(dto, "Новое бронирование");
            case NotificationType.CONFIRMED_BOOKING -> response = sendNotification(dto, "Бронь подтверждена");
            case NotificationType.REJECTED_BOOKING -> response = sendNotification(dto, "Бронь отменена");
        }

        return response;
    }

    // Метод будет отправлять уведомления в зависимости от полученного типа, наверное через свитч в будущем
    // Сейчас будет только отправка не почту
    public NotificationResponseDto sendNotification(NotificationCreateDto dto, String message) {
        log.info("Начинаю отправку уведомления пользователю с id = {}", dto.getUserId());
        UserRequestDto user = userClient.getUserById(dto.getUserId())
                .orElseThrow(() -> {
                    log.warn("Пользователь с id = {} не найден", dto.getUserId());
                    return new UserNotFoundException(String.format("Пользователь с id = %d не найден", dto.getUserId()));
                });
        var status = sendEmail(user.getEmail(), message);
        return saveNotification(dto, message, status);
    }

    // Отправляем голубя
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
}
