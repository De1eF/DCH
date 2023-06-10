package budkevych.squareapi.service.impl;

import budkevych.squareapi.config.ConfigProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private static final String MAIL_HOST = "smtp.gmail.com";
    private static final String MAIL_USERNAME = "horyzont.auth@gmail.com";
    private static final String MAIL_APP_PASSWORD = "ehpfpjpgwjkuvehh";

    public void sendEmail(String toEmail,
                          String subject,
                          String body) throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(MAIL_HOST);
        mailSender.setPort(587);
        mailSender.setUsername(MAIL_USERNAME);
        mailSender.setPassword(MAIL_APP_PASSWORD);

        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(mailProperties);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(MAIL_USERNAME);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);

        mailSender.send(message);
        System.out.println("Email has been sent to " + toEmail);
    }
}
