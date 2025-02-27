package ru.astongroup.carbooking.exception;

import lombok.Getter;

@Getter
public class ClientRequestException extends RuntimeException {
    private final String reason = "The required object was not found.";

    public ClientRequestException(String message) {
        super(message);
    }
}