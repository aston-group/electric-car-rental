package ru.astongroup.notifications.exception;

import lombok.Getter;

@Getter
public class NotificationNotFoundException extends RuntimeException {
    private final String reason = "The required object was not found.";

    public NotificationNotFoundException(String message) {
        super(message);
    }
}
