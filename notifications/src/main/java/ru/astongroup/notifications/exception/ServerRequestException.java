package ru.astongroup.notifications.exception;

import lombok.Getter;

@Getter
public class ServerRequestException extends RuntimeException {
    private final String reason = "Server Error.";

    public ServerRequestException(String message) {
        super(message);
    }
}