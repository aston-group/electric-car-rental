package ru.astongroup.notifications.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.astongroup.notifications.dto.ChangeNotificationStatusDto;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.entity.NotificationStatus;
import ru.astongroup.notifications.service.NotificationService;

import java.util.Collection;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Collection<NotificationResponseDto>> getAll() {
        Collection<NotificationResponseDto> notifications = notificationService.getAll();

        return new ResponseEntity<>(notifications, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDto> findById(@PathVariable Long notificationId) {
        NotificationResponseDto notification = notificationService.getDtoById(notificationId);

        return new ResponseEntity<>(notification, HttpStatus.ACCEPTED);
    }

    @GetMapping("/status")
    public ResponseEntity<Collection<NotificationResponseDto>> findByStatus(
            @RequestParam(defaultValue = "SUCCESS") NotificationStatus status) {
        Collection<NotificationResponseDto> notifications = notificationService.getByStatus(status);

        return new ResponseEntity<>(notifications, HttpStatus.ACCEPTED);
    }

    @PatchMapping
    public ResponseEntity<NotificationResponseDto> changeNotificationStatus(
            @Valid @RequestBody ChangeNotificationStatusDto dto) {
        NotificationResponseDto notification = notificationService.changeStatus(dto);

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDto> createNotification(@Valid @RequestBody NotificationCreateDto dto) {
        NotificationResponseDto response = notificationService.createNotification(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotificationById(@PathVariable Long notificationId) {
        notificationService.deleteById(notificationId);
    }
}