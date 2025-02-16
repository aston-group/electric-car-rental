package ru.astongroup.usermanagement.utils.exceptions;

public class EmailAlreadyBusyException extends RuntimeException {
    public EmailAlreadyBusyException(String message) {
        super(message);
    }
}
