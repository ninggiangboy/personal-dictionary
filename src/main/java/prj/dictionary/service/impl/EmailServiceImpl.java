package prj.dictionary.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import prj.dictionary.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender mailSender;

    public void send(String subject, String content, String to, Boolean isHtmlFormat) throws MessagingException {
        if (isHtmlFormat == null) {
            isHtmlFormat = false;
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

        message.setSubject(subject);
        message.setText(content, isHtmlFormat);
        message.setTo(to);

        mailSender.send(mimeMessage);
    }
}
