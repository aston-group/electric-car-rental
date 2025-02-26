package ru.astongroup.notifications.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.astongroup.notifications.dto.ChangeNotificationStatusDto;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.entity.NotificationStatus;
import ru.astongroup.notifications.entity.NotificationType;
import ru.astongroup.notifications.exception.NotificationNotFoundException;
import ru.astongroup.notifications.exception.UserNotFoundException;
import ru.astongroup.notifications.repository.NotificationRepository;
import ru.astongroup.notifications.service.NotificationService;

import java.util.Collection;
import java.util.List;
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
    void createNotificationValidDtoReturnsCreatedStatusAndResponse() {
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
    void createNotificationEmailSendingFailedReturnsResponseWithFailedStatus() {
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
    void createNotificationUserNotFoundThrowsUserNotFoundException() {
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
    void createNotificationConfirmedBookingTypeReturnsCorrectMessage() {
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

    @Test
    void getAllReturnsListOfNotifications() {
        NotificationResponseDto notification1 = NotificationResponseDto.builder()
                .userId(1L)
                .bookingId(1L)
                .status(NotificationStatus.SUCCESS)
                .message("Уведомление 1")
                .build();
        NotificationResponseDto notification2 = NotificationResponseDto.builder()
                .userId(2L)
                .bookingId(2L)
                .status(NotificationStatus.ERROR)
                .message("Уведомление 2")
                .build();
        Collection<NotificationResponseDto> notifications = List.of(notification1, notification2);

        Mockito.when(notificationService.getAll()).thenReturn(notifications);

        ResponseEntity<Collection<NotificationResponseDto>> response = notificationController.getAll();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(notificationService, times(1)).getAll();
    }

    @Test
    void findById_ValidId_ReturnsNotification() {
        // Подготовка данных
        Long notificationId = 1L;
        NotificationResponseDto notification = NotificationResponseDto.builder()
                .userId(1L)
                .bookingId(1L)
                .status(NotificationStatus.SUCCESS)
                .message("Уведомление 1")
                .build();

        // Мокирование
        Mockito.when(notificationService.getDtoById(notificationId)).thenReturn(notification);

        // Вызов метода
        ResponseEntity<NotificationResponseDto> response = notificationController.findById(notificationId);

        // Проверки
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(notification, response.getBody());
        verify(notificationService, times(1)).getDtoById(notificationId);
    }

    @Test
    void findByIdInvalidIdThrowsNotificationNotFoundException() {
        // Подготовка данных
        Long notificationId = 99L;

        Mockito.when(notificationService.getDtoById(notificationId))
                .thenThrow(new NotificationNotFoundException("Уведомление с id = 99 не найдено"));

        assertThrows(NotificationNotFoundException.class, () -> {
            notificationController.findById(notificationId);
        });

        verify(notificationService, times(1)).getDtoById(notificationId);
    }

    @Test
    void findByStatusValidStatusReturnsNotifications() {
        NotificationStatus status = NotificationStatus.SUCCESS;
        NotificationResponseDto notification1 = NotificationResponseDto.builder()
                .userId(1L)
                .bookingId(1L)
                .status(status)
                .message("Уведомление 1")
                .build();
        NotificationResponseDto notification2 = NotificationResponseDto.builder()
                .userId(2L)
                .bookingId(2L)
                .status(status)
                .message("Уведомление 2")
                .build();
        Collection<NotificationResponseDto> notifications = List.of(notification1, notification2);

        Mockito.when(notificationService.getByStatus(status)).thenReturn(notifications);

        ResponseEntity<Collection<NotificationResponseDto>> response = notificationController.findByStatus(status);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(notificationService, times(1)).getByStatus(status);
    }

    @Test
    void changeNotificationStatusValidDtoReturnsUpdatedNotification() {
        ChangeNotificationStatusDto dto = ChangeNotificationStatusDto.builder()
                .notificationId(1L)
                .status(NotificationStatus.ERROR)
                .build();
        NotificationResponseDto updatedNotification = NotificationResponseDto.builder()
                .userId(1L)
                .bookingId(1L)
                .status(NotificationStatus.ERROR)
                .message("Уведомление обновлено")
                .build();

        Mockito.when(notificationService.changeStatus(dto)).thenReturn(updatedNotification);

        ResponseEntity<NotificationResponseDto> response = notificationController.changeNotificationStatus(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNotification, response.getBody());
        verify(notificationService, times(1)).changeStatus(dto);
    }

    @Test
    void deleteNotificationById_ValidId_DeletesNotification() {
        // Подготовка данных
        Long notificationId = 1L;

        // Вызов метода
        notificationController.deleteNotificationById(notificationId);

        // Проверки
        verify(notificationService, times(1)).deleteById(notificationId);
    }

    @Test
    void deleteNotificationByIdInvalidIdThrowsNotificationNotFoundException() {
        Long notificationId = 99L;

        Mockito.doThrow(new NotificationNotFoundException("Уведомление с id = 99 не найдено"))
                .when(notificationService).deleteById(notificationId);

        assertThrows(NotificationNotFoundException.class, () -> {
            notificationController.deleteNotificationById(notificationId);
        });

        verify(notificationService, times(1)).deleteById(notificationId);
    }
}
