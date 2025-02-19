package ru.astongroup.notifications.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.astongroup.notifications.entity.NotificationType;

@Data
@Builder
public class NotificationCreateDto {
    @NotNull
    private Long bookingId;
    @NotNull
    private Long userId;
    @NotNull
    private NotificationType notificationType;
}
