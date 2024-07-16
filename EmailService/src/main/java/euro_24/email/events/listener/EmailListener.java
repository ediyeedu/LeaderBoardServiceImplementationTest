package euro_24.email.events.listener;



import com.fasterxml.jackson.databind.ObjectMapper;
import euro_24.email.dto.PrizeDto;
import euro_24.email.model.Email;
import euro_24.email.repository.EmailRepository;
import euro_24.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailListener {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final EmailRepository emailRepository;




        @KafkaListener(topics = "email", groupId = "email-group")
        public void emailListener (@Payload PrizeDto prizeDto) {
            try {
                String body = String.format (
                        "<html>" +
                                "<body>" +
                                "<h2>Congratulations %s %s!</h2>" +
                                "<p>You have won a %s prize for match %d.</p>" +
                                "<p>Details: %s</p>" +
                                "</body>" +
                                "</html>",
                        prizeDto.getFirstName (), // First name
                        prizeDto.getLastName (), // Last name
                        prizeDto.getPrizeType (), // Prize type
                        prizeDto.getMatchId (), // Match ID should be an integer or long, ensure formatting matches
                        prizeDto.getPrizeDescription () // Prize description
                );
                Email email = Email.builder ()
                        .receiverEmail ( prizeDto.getEmail () ) // Use the email from PrizeDto
                        .subject ( "Prize Notification" )
                        .body ( body )
                        .build ();

                emailService.sendWithHtmlBody ( email );
                emailRepository.save ( email );
            } catch (Exception e) {
                e.printStackTrace ();
            }

        }
}
