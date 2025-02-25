package ru.astongroup.notifications.service;

import ru.astongroup.notifications.entity.EmailDetails;
import ru.astongroup.notifications.entity.NotificationStatus;

public interface EmailService {

    NotificationStatus sendSimpleEmail(EmailDetails details);
}
