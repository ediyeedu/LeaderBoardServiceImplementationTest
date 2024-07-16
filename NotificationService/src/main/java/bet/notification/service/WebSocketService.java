package bet.notification.service;


import bet.notification.model.GlobalNotification;
import bet.notification.model.UserNotification;
import bet.notification.payload.NotificationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;


    public void sendUserNotification(UserNotification message) {
        messagingTemplate.convertAndSendToUser(message.getUsername(), "/queue/notifications", message.getContent());
    }

    public void sendGlobalNotification(GlobalNotification message) {
        messagingTemplate.convertAndSend("/topic/notifications", message.getMessage ());
    }
    public void sendNotificationToFrontEnd(NotificationDto notificationDto){
        try{
            String message = objectMapper.writeValueAsString (notificationDto);
            messagingTemplate.convertAndSend ( "/topic/leaderBoard", message);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
