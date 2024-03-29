package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.config.JavaMailServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private static JavaMailSenderImpl mailSender;

    private final JavaMailServiceImpl javaMailServiceImpl;

    @PostConstruct
    private void postConstruct() {
        mailSender = javaMailServiceImpl.mailSender();
    }

    public void sendEmail(String toEmail,
                          String subject,
                          String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setContent(body, "text/html; charset=utf-8");
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(mailSender.getUsername());
            helper.setTo(toEmail);
            helper.setSubject(subject);

            mailSender.send(message);
            System.out.println("Email has been sent to " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to message " + toEmail, e);
        }
    }
}
