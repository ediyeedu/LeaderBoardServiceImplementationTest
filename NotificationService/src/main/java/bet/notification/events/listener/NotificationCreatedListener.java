package bet.notification.events.listener;


import bet.notification.components.NotificationHandler;
import bet.notification.model.GlobalNotification;
import bet.notification.model.Notification;
import bet.notification.payload.NotificationDto;
import bet.notification.payload.PrizeDto;
import bet.notification.repository.GlobalNotificationRepository;
import bet.notification.repository.NotificationRepository;
import bet.notification.service.NotificationService;
import bet.notification.service.WebSocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationCreatedListener {


  @Autowired
  private NotificationHandler notificationHandler;

  private final ObjectMapper objectMapper;

  private final WebSocketService webSocketService;


    private GlobalNotificationRepository repository;

    @KafkaListener(topics = {"notification"})
    public void listenerWhenNotificationtCreated(@Payload PrizeDto prizeDto) {

        try {
            GlobalNotification Globalnotification = GlobalNotification.builder()
                    .message(String.format("Congratulations %s %s! You have won a %s prize for match %d.",
                            prizeDto.getFirstName(), prizeDto.getLastName(), prizeDto.getPrizeType(), prizeDto.getMatchId()))
                    .timeStamp( LocalDateTime.now())
                    .build();

            NotificationDto notificationDto = NotificationDto.builder()
                    .message ( Globalnotification.getMessage ())

                    .build();
            repository.save(Globalnotification);
            webSocketService.sendGlobalNotification (Globalnotification);

//            NotificationHandler.sendMessageToAll (prizeDto);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


}
