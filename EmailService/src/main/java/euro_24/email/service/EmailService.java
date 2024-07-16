package euro_24.email.service;




import euro_24.email.dto.request.EmailRequest;
import euro_24.email.dto.response.EmailResponse;
import euro_24.email.model.Email;

import java.util.List;


public interface EmailService {

   EmailResponse findEmailById(Long id);
   List<EmailResponse> findAllEmails();
   String saveEmail(Email email);
   String saveEmails(List<Email> email);
   String deleteById(Long id);
   String deleteAll();

   void send(Email email);

   String  sendEmail(EmailRequest emailRequest);

   void sendWithHtmlBody(Email email);

   List<EmailResponse>  allEmails();

}
