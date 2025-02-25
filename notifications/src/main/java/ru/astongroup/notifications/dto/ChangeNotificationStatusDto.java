package ru.astongroup.notifications.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.astongroup.notifications.entity.NotificationStatus;

@Builder
@Getter
@Setter
public class ChangeNotificationStatusDto {

    @NotNull
    private Long notificationId;
    @NotNull
    private NotificationStatus status;
}
