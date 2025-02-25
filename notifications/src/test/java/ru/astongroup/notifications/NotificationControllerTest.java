package ru.astongroup.notifications;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.astongroup.notifications.controller.NotificationController;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.entity.NotificationStatus;
import ru.astongroup.notifications.entity.NotificationType;
import ru.astongroup.notifications.exception.UserNotFoundException;
import ru.astongroup.notifications.repository.NotificationRepository;
import ru.astongroup.notifications.service.NotificationService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    void createNotification_ValidDto_ReturnsCreatedStatusAndResponse() {
        NotificationCreateDto createDto = NotificationCreateDto.builder()
                .bookingId(1L)
                .userId(2L)
                .notificationType(NotificationType.NEW_BOOKING)
                .build();

        NotificationResponseDto expectedResponse = NotificationResponseDto.builder()
                .userId(2L)
                .bookingId(1L)
                .status(NotificationStatus.SUCCESS)
                .message("Новое бронирование")
                .build();

        Mockito.when(notificationService.createNotification(createDto)).thenReturn(expectedResponse);

        ResponseEntity<NotificationResponseDto> response = notificationController.createNotification(createDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(notificationService, times(1)).createNotification(createDto);
    }

    @Test
    void createNotification_EmailSendingFailed_ReturnsResponseWithFailedStatus() {
        NotificationCreateDto createDto = NotificationCreateDto.builder()
                .bookingId(3L)
                .userId(4L)
                .notificationType(NotificationType.REJECTED_BOOKING)
                .build();

        NotificationResponseDto expectedResponse = NotificationResponseDto.builder()
                .userId(4L)
                .bookingId(3L)
                .status(NotificationStatus.ERROR)
                .message("Бронь отменена")
                .build();

        Mockito.when(notificationService.createNotification(createDto)).thenReturn(expectedResponse);

        ResponseEntity<NotificationResponseDto> response = notificationController.createNotification(createDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(NotificationStatus.ERROR, Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("Бронь отменена", response.getBody().getMessage());
    }

    @Test
    void createNotification_UserNotFound_ThrowsUserNotFoundException() {
        NotificationCreateDto createDto = NotificationCreateDto.builder()
                .bookingId(5L)
                .userId(99L)
                .notificationType(NotificationType.NEW_BOOKING)
                .build();

        Mockito.when(notificationService.createNotification(createDto))
                .thenThrow(new UserNotFoundException("Пользователь с id = 99 не найден"));

        assertThrows(UserNotFoundException.class, () -> {
            notificationController.createNotification(createDto);
        });

        verify(notificationService, times(1)).createNotification(createDto);
    }

    @Test
    void createNotification_ConfirmedBookingType_ReturnsCorrectMessage() {
        NotificationCreateDto createDto = NotificationCreateDto.builder()
                .bookingId(6L)
                .userId(7L)
                .notificationType(NotificationType.CONFIRMED_BOOKING)
                .build();

        NotificationResponseDto expectedResponse = NotificationResponseDto.builder()
                .userId(7L)
                .bookingId(6L)
                .status(NotificationStatus.SUCCESS)
                .message("Бронь подтверждена")
                .build();

        Mockito.when(notificationService.createNotification(createDto)).thenReturn(expectedResponse);

        ResponseEntity<NotificationResponseDto> response = notificationController.createNotification(createDto);

        assertEquals("Бронь подтверждена", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(NotificationStatus.SUCCESS, response.getBody().getStatus());
    }
}
