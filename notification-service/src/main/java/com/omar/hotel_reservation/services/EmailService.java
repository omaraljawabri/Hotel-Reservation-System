package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.producers.auth.AuthConfirmation;
import com.omar.hotel_reservation.producers.payment.PaymentKafka;
import com.omar.hotel_reservation.producers.reservation.ReservationKafka;
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
    public void sendAuthEmail(AuthConfirmation authConfirmation, String templateName, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        helper.setFrom(MAIL_SENDER);

        Context context = new Context();
        context.setVariable("firstName", authConfirmation.firstName());
        context.setVariable("lastName", authConfirmation.lastName());
        context.setVariable("verificationLink", authConfirmation.verificationLink());
        helper.setSubject(subject);

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

    @Async
    public void sendReservationEmail(ReservationKafka reservationKafka, String templateName, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        helper.setFrom(MAIL_SENDER);

        Context context = new Context();
        context.setVariable("firstName", reservationKafka.firstName());
        context.setVariable("lastName", reservationKafka.lastName());
        context.setVariable("roomNumber", reservationKafka.roomNumber());
        context.setVariable("hotelName", reservationKafka.hotelName());
        context.setVariable("country", reservationKafka.country());
        context.setVariable("state", reservationKafka.state());
        context.setVariable("city", reservationKafka.city());
        context.setVariable("checkInDate", reservationKafka.checkInDate());
        context.setVariable("checkOutDate", reservationKafka.checkOutDate());
        helper.setSubject(subject);
        try {
            String htmlContent = springTemplateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            helper.setTo(reservationKafka.email());
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", reservationKafka.email(), templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", reservationKafka.email());
        }
    }

    @Async
    public void sendPaymentEmail(PaymentKafka paymentKafka, String templateName, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        helper.setFrom(MAIL_SENDER);

        Context context = new Context();
        context.setVariable("firstName", paymentKafka.firstName());
        context.setVariable("lastName", paymentKafka.lastName());
        context.setVariable("method", paymentKafka.method());
        context.setVariable("amount", paymentKafka.amount());
        context.setVariable("currency", paymentKafka.currency());
        context.setVariable("paymentDate", paymentKafka.paymentDate());
        context.setVariable("hotelName", paymentKafka.hotelName());
        context.setVariable("roomNumber", paymentKafka.roomNumber());
        helper.setSubject(subject);

        try {
            String htmlContent = springTemplateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            helper.setTo(paymentKafka.email());
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", paymentKafka.email(), templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", paymentKafka.email());
        }
    }
}
