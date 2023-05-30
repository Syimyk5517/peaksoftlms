package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    @Override
    public void emailSender(String toEmail, String link) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("medcheck.service@gmail.com");
            helper.setTo(toEmail);
//            helper.setText(body, true);
//            helper.setSubject(subject);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new NotFoundException("Email send failed!");
        } catch (NullPointerException e) {
            throw new NotFoundException("Not found and returned null!");
        }
    }
}
