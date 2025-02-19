package ru.astongroup.notifications.service;

import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.dto.SubscriptionCreateDto;
import ru.astongroup.notifications.dto.SubscriptionResponseDto;

public interface NotificationService {

    NotificationResponseDto createNotification(NotificationCreateDto dto);
}
