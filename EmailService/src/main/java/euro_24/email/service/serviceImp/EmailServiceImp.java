package euro_24.email.service.serviceImp;




import euro_24.email.dto.request.EmailRequest;
import euro_24.email.dto.response.EmailResponse;
import euro_24.email.model.Email;
import euro_24.email.repository.EmailRepository;
import euro_24.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {



    @Value ("{mail.username")
    private String SENDER_ADDRESS;
    @Autowired
    private  JavaMailSender javaMailSender;

    private final ModelMapper modelMapper;

    private final EmailRepository emailRepository;


    @Override
    public EmailResponse findEmailById(Long id) {
        return emailRepository.findById(id)
                .map(email -> modelMapper.map(email,EmailResponse.class))
                .orElse(null);
    }

    @Override
    public List<EmailResponse> findAllEmails() {
        return emailRepository.findAll()
                .stream()
                .map(email -> modelMapper.map(email, EmailResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public String saveEmail(Email email) {
        emailRepository.save(email);
        return "Email saved successfully";
    }

    @Override
    public String saveEmails(List<Email> email) {

      emailRepository.saveAll (email);
        return "Emails saved successfully";
    }

    @Override
    public String deleteById(Long id) {
        emailRepository.deleteById(id);
        return "Email deleted successfully";
    }

    @Override
    public String deleteAll() {
        emailRepository.deleteAll();
        return "All emails deleted successfully";
    }


    @Override
    public void send(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(SENDER_ADDRESS);
            message.setTo(email.getReceiverEmail());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            javaMailSender.send(message);
        } catch (MailException mailException) {
            mailException.printStackTrace();
        }
    }

    @Override
    public String sendEmail(EmailRequest emailRequest) {
        if (emailRequest.getSubject() == null) {
            return "Subject is missing";
        }
        if (emailRequest.getReceiverEmail() == null) {
            return "Email is missing";
        }
        if (emailRequest.getBody() == null) {
            return "Body is missing";
        }

        Email email = modelMapper.map(emailRequest, Email.class);
        send(email);
        emailRepository.save(email);
        return "Email sent successfully";
    }

    @Override
    public void sendWithHtmlBody(Email email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(SENDER_ADDRESS);
            mimeMessageHelper.setTo(email.getReceiverEmail());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getBody(), true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<EmailResponse> allEmails() {
        return emailRepository.findAll()
                .stream()
                .map(email -> modelMapper.map(email, EmailResponse.class))
                .collect(Collectors.toList());
    }




}
