package ru.astongroup.notifications.service;

import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;

public interface NotificationService {

    NotificationResponseDto createNotification(NotificationCreateDto dto);
}
