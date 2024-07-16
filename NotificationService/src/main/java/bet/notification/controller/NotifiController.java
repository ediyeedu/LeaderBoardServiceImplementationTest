package bet.notification.controller;
import bet.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
public class NotifiController {

    private final NotificationService notificationService;


    @GetMapping("all-notification")
    public ResponseEntity<?> findAllNotifications(){
        return ResponseEntity.status (HttpStatus.OK).body (notificationService.getAllNotification ());
    }







}
