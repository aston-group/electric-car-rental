package ru.astongroup.notifications.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astongroup.notifications.client.UserClient;
import ru.astongroup.notifications.dto.NotificationCreateDto;
import ru.astongroup.notifications.dto.NotificationResponseDto;
import ru.astongroup.notifications.dto.UserRequestDto;
import ru.astongroup.notifications.entity.EmailDetails;
import ru.astongroup.notifications.entity.Notification;
import ru.astongroup.notifications.entity.NotificationStatus;
import ru.astongroup.notifications.entity.NotificationType;
import ru.astongroup.notifications.exception.UserNotFoundException;
import ru.astongroup.notifications.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    EmailService emailService;

    @Mock
    UserClient userClient;

    @Test
    void shouldBePositiveWhenCreateNewNotification() {
        Long userId = 1L;
        Long bookingId = 1L;
        String email = "user1@user.ru";
        NotificationCreateDto notificationCreateDto = NotificationCreateDto.builder()
                .bookingId(bookingId)
                .userId(userId)
                .notificationType(NotificationType.NEW_BOOKING)
                .build();
        Notification notification = Notification.builder()
                .bookingId(bookingId)
                .userId(userId)
                .message("Hello")
                .build();
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .id(userId)
                .name("user 1")
                .email(email)
                .build();

        Mockito
                .when(userClient.getUserById(userId))
                .thenReturn(Optional.ofNullable(userRequestDto));
        Mockito
                .when(notificationRepository.save(Mockito.any(Notification.class)))
                .thenReturn(notification);
        Mockito
                .when(emailService.sendSimpleEmail(Mockito.any(EmailDetails.class)))
                .thenReturn(NotificationStatus.SUCCESS);

        NotificationResponseDto responseDto = notificationService.createNotification(notificationCreateDto);

        assertEquals(userId, responseDto.getUserId());
    }

    @Test
    public void shouldBeThrownExceptionWhenUserNotFound() {
        Long userId = 1L;
        Long bookingId = 1L;
        String email = "user1@user.ru";
        NotificationCreateDto notificationCreateDto = NotificationCreateDto.builder()
                .bookingId(bookingId)
                .userId(userId)
                .notificationType(NotificationType.NEW_BOOKING)
                .build();

        Mockito
                .when(userClient.getUserById(userId))
                .thenThrow(new UserNotFoundException("Пользователь не найден"));

        assertThrows(UserNotFoundException.class,
                () -> notificationService.createNotification(notificationCreateDto));
    }

    @Test
    public void shouldReturnCollectionNotificationResponseDto() {
        Notification notification = Notification.builder()
                .bookingId(1L)
                .userId(1L)
                .build();

        Mockito
                .when(notificationRepository.findAll())
                .thenReturn(List.of(notification));

        var response = notificationService.getAll();

        assertEquals(1, response.size());
    }
}