package prj.dictionary.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void send(String subject, String content, String to, Boolean isHtmlFormat) throws MessagingException;
}
