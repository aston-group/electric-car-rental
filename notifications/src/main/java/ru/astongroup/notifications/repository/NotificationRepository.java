package ru.astongroup.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.astongroup.notifications.entity.Notification;
import ru.astongroup.notifications.entity.NotificationStatus;

import java.util.Collection;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = """
                SELECT n
                FROM Notification n
                WHERE n.status = :status
            """)
    Collection<Notification> finAllByStatus(NotificationStatus status);
}
