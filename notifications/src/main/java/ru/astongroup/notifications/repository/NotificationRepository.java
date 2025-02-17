package ru.astongroup.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astongroup.notifications.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
