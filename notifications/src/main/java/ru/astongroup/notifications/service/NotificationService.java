package ru.astongroup.notifications.service;

import jakarta.validation.Valid;
import ru.astongroup.notifications.dto.ChangeNotificationStatusDto;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.entity.NotificationStatus;

import java.util.Collection;

public interface NotificationService {

    NotificationResponseDto createNotification(NotificationCreateDto dto);

    Collection<NotificationResponseDto> getAll();

    NotificationResponseDto getDtoById(Long notificationId);

    NotificationResponseDto changeStatus(@Valid ChangeNotificationStatusDto dto);

    void deleteById(Long notificationId);

    Collection<NotificationResponseDto> getByStatus(NotificationStatus status);
}
