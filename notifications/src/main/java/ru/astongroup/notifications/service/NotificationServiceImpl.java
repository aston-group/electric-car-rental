package ru.astongroup.notifications.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.entity.Notification;
import ru.astongroup.notifications.mapper.NotificationMapper;
import ru.astongroup.notifications.repository.NotificationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponseDto createNotification(NotificationCreateDto dto) {

        log.info("Пытаюсь создать новое уведомлений для пользователя с id = {}", dto.getUserId());
        Notification notification = notificationRepository.save(NotificationMapper.matToNotification(dto));
        log.info("Создано уведомление для пользователя с id = {} на авто с id = {}", dto.getUserId(), dto.getCarId());
        return NotificationMapper.mapToResponseDto(notification);
    }
}
