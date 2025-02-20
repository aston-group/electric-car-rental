package ru.astongroup.notifications.mapper;

import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.entity.Notification;
import ru.astongroup.notifications.entity.NotificationStatus;

import java.time.LocalDateTime;

public class NotificationMapper {

    private NotificationMapper() {

    }

    public static Notification matToNotification(NotificationCreateDto dto, String message, NotificationStatus status) {
        return Notification.builder()
                .userId(dto.getUserId())
                .bookingId(dto.getBookingId())
                .createdOn(LocalDateTime.now())
                .type(dto.getNotificationType())
                .message(message)
                .status(status)
                .build();
    }

    public static NotificationResponseDto mapToResponseDto(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .bookingId(notification.getBookingId())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .createdOn(notification.getCreatedOn())
                .build();
    }
}
