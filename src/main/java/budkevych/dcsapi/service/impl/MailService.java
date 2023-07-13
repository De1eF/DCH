package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.config.JavaMailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailServiceImpl javaMailServiceImpl;

    public void sendEmail(String toEmail,
                          String subject,
                          String body) {
        JavaMailSenderImpl mailSender = javaMailServiceImpl.mailSender();

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
