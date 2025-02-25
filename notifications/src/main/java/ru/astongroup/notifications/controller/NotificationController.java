package ru.astongroup.notifications.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Notifications", description = "API для управления уведомлениями")
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Получить все уведомления", description = "Возвращает список всех уведомлений.")
    @GetMapping
    public ResponseEntity<Collection<NotificationResponseDto>> getAll() {
        Collection<NotificationResponseDto> notifications = notificationService.getAll();

        return new ResponseEntity<>(notifications, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Получить уведомление по Id", description = "Возвращает уведомление с указанным Id.")
    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDto> findById(@Parameter(description = "Id уведомления", required = true)
                                                            @PathVariable Long notificationId) {
        NotificationResponseDto notification = notificationService.getDtoById(notificationId);

        return new ResponseEntity<>(notification, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Получить уведомления по статусу", description = "Возвращает список уведомлений с указанным статусом.")
    @GetMapping("/status")
    public ResponseEntity<Collection<NotificationResponseDto>> findByStatus(
            @Parameter(description = "Статус уведомления")
            @RequestParam(defaultValue = "SUCCESS") NotificationStatus status) {
        Collection<NotificationResponseDto> notifications = notificationService.getByStatus(status);

        return new ResponseEntity<>(notifications, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Изменить статус уведомления", description = "Обновляет статус указанного уведомления.")
    @PatchMapping
    public ResponseEntity<NotificationResponseDto> changeNotificationStatus(
            @Valid @RequestBody ChangeNotificationStatusDto dto) {
        NotificationResponseDto notification = notificationService.changeStatus(dto);

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @Operation(summary = "Создать новое уведомление", description = "Создает новое уведомление.")
    @PostMapping
    public ResponseEntity<NotificationResponseDto> createNotification(@Valid @RequestBody NotificationCreateDto dto) {
        NotificationResponseDto response = notificationService.createNotification(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Удалить уведомление по Id", description = "Удаляет уведомление с указанным Id.")
    @DeleteMapping("/{notificationId}")
    public void deleteNotificationById(
            @Parameter(description = "Id уведомления", required = true)
            @PathVariable Long notificationId) {
        notificationService.deleteById(notificationId);
    }
}