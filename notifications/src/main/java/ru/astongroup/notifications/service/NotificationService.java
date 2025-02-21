package ru.astongroup.notifications.service;

import jakarta.validation.Valid;
import ru.astongroup.notifications.dto.ChangeNotificationStatusDto;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;

import java.util.Collection;

public interface NotificationService {

    NotificationResponseDto createNotification(NotificationCreateDto dto);

    Collection<NotificationResponseDto> getAll();

    NotificationResponseDto getDtoById(Long notificationId);

    NotificationResponseDto changeStatus(@Valid ChangeNotificationStatusDto dto);

    void deleteById(Long notificationId);
}
