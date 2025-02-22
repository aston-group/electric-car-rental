package ru.astongroup.notifications.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class,
            ValidationException.class,
            NumberFormatException.class,
            HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> invalidMethodArgument(Exception e, WebRequest request) {
        log.error("Ошибка 400 {}: {} в запросе {}",
                e.getClass(), e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Incorrectly made request.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleClientRequestExc(final ClientRequestException e, WebRequest request) {
        log.error("Ошибка 404 ClientRequestException: {} в запросе {}",
                e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getReason());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleServerRequestExc(final ServerRequestException e, WebRequest request) {
        log.error("Ошибка 500 ServerRequestException: {} в запросе {}",
                e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getReason());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound(final UserNotFoundException e, WebRequest request) {
        log.error("Ошибка 404 UserNotFoundException: {} в запросе {}",
                e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getReason());
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotificationNotFound(final NotificationNotFoundException e, WebRequest request) {
        log.error("Ошибка 404 NotificationNotFoundException: {} в запросе {}",
                e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getReason());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> handleEmailSendExc(final SendEmailException e, WebRequest request) {
        log.error("Ошибка 503 SendEmailException: {} в запросе {}",
                e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.SERVICE_UNAVAILABLE, e.getReason());
    }

    public Map<String, String> buildErrorResponse(Exception e, HttpStatus status, String reason) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("status", status.name());
        response.put("reason", reason);
        response.put("message", e.getMessage());
        response.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return response;
    }


}