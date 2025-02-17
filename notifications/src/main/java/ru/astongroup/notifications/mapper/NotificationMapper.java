package ru.astongroup.notifications.mapper;

import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.entity.Notification;

import java.time.LocalDateTime;

public class NotificationMapper {

    private NotificationMapper() {

    }

    public static Notification matToNotification(NotificationCreateDto dto) {
        return Notification.builder()
                .userId(dto.getUserId())
                .carId(dto.getCarId())
                .bookingStart(dto.getBookingStart())
                .bookingEnd(dto.getBookingEnd())
                .createdOn(LocalDateTime.now())
                .build();
    }

    public static NotificationResponseDto mapToResponseDto(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .carId(notification.getCarId())
                .bookingStart(notification.getBookingStart())
                .bookingEnd(notification.getBookingEnd())
                .createdOn(notification.getCreatedOn())
                .build();
    }
}
