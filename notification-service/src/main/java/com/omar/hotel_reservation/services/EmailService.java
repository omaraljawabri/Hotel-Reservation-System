package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.producers.auth.AuthConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;

    private static final String MAIL_SENDER = "omar@omartest.com";

    @Async
    public void sendAuthEmail(AuthConfirmation authConfirmation, String templateName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        helper.setFrom(MAIL_SENDER);

        Context context = new Context();
        context.setVariable("firstName", authConfirmation.firstName());
        context.setVariable("lastName", authConfirmation.lastName());
        context.setVariable("verificationLink", authConfirmation.verificationLink());
        helper.setSubject("Account confirmation");

        try {
            String htmlContent = springTemplateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            helper.setTo(authConfirmation.email());
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", authConfirmation.email(), templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", authConfirmation.email());
        }
    }
}
