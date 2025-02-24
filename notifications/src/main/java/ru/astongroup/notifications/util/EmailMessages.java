package ru.astongroup.notifications.util;

import ru.astongroup.notifications.dto.UserRequestDto;
import ru.astongroup.notifications.entity.NotificationType;

import java.util.HashMap;
import java.util.Map;

public final class EmailMessages {
    private EmailMessages() {
    }
    public static final String NEW_BOOKING_TEXT = "Уважаемый,  %s!\nПоздравляем, вы только что отправили заявку" +
            " на бронирование автомобиля!";
    public static final String CONFIRMED_BOOKING_TEXT = "Уважаемый,  %s!\nПоздравляем, ваше бронирование подтверждено!";
    public static final String REJECTED_BOOKING_TEXT = "Уважаемый,  %s!\nОчень жаль, но бронирование отменено.";
    public static final Map<NotificationType, String> messages;

    static {
        messages = new HashMap<>();
        messages.put(NotificationType.NEW_BOOKING, NEW_BOOKING_TEXT);
        messages.put(NotificationType.CONFIRMED_BOOKING, CONFIRMED_BOOKING_TEXT);
        messages.put(NotificationType.REJECTED_BOOKING, REJECTED_BOOKING_TEXT);
    }

    public static String createMessage(UserRequestDto user, NotificationType type) {
        return String.format(messages.get(type), user.getName());
    }
}
