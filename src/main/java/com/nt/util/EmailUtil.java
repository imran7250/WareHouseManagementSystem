package com.nt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;  // Added for @PostConstruct
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

@Component
public class EmailUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @PostConstruct  // Runs after injection to trim whitespace
    public void init() {
        fromEmail = fromEmail.trim();
    }

    public boolean sendEmail(String to, String subject, String body) {
        try {
            // Validate fromEmail before using
            new InternetAddress(fromEmail).validate();  // Throws exception if invalid

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
            return true;
        } catch (AddressException e) {
            logger.error("Invalid 'from' address: {}. Error: {}", fromEmail, e.getMessage(), e);
            return false;
        } catch (MailException e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage(), e);
            return false;
        }
    }
}
