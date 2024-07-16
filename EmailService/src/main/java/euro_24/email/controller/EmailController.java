package euro_24.email.controller;



import euro_24.email.dto.request.EmailRequest;
import euro_24.email.model.Email;
import euro_24.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email-service/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    
     @GetMapping("/healthcheck")
    public String healthCheck(){
        return "Email-service working Fine";
    }

    @GetMapping("hello")
    public String hello(){
        return "Hello";
    }

//    @PostMapping("send")
//    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest){
//        return ResponseEntity.status ( HttpStatus.OK).body ( emailService.sendEmail (emailRequest));
//    }

    @GetMapping("emails")
    public ResponseEntity<?> allEmails(){
        return ResponseEntity.status ( HttpStatus.OK).body ( emailService.allEmails ());
    }

    @PostMapping("create")
    public  String createEmail (Email email){
        emailService.saveEmail (email);
        return "email";
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody Email email) {
        emailService.sendWithHtmlBody(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
