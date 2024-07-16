package bet.notification.service;




import bet.notification.model.Notification;

import java.util.List;

public interface NotificationService {

    public void createNotification(Notification messageRequest);

    public String CreateNotifications(List<Notification> messages);

    public Notification getNotification(Long id);

    public List<Notification> getAllNotification();

    public String upDateNotification(Notification notification, Long id);

    public String deleteNotification(Long id);
    public String deleteAll();

    public Notification sendNotification();
}
