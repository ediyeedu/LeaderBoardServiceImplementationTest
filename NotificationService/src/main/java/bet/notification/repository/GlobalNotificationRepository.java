package bet.notification.repository;

import bet.notification.model.GlobalNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalNotificationRepository extends JpaRepository<GlobalNotification,Long> {
}
