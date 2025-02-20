package ru.astongroup.notifications.exception;

import lombok.Getter;

@Getter
public class SendEmailException extends RuntimeException {
    private final String reason = "The required object was not found.";

    public SendEmailException(String message) {
        super(message);
    }
}