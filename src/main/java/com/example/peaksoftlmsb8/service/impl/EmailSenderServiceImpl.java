package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;

    @Override
    public void emailSender(String toEmail, String link) {
        User user = userRepository.findByEmail(toEmail).orElseThrow(
                () -> new NotFoundException("User with email: " + toEmail + " not found !"));
        Context context = new Context();
        context.setVariable("firstMessage", String.format("Здравствуйте %s %s", user.getFirstName(), user.getLastName()));
        context.setVariable("link", link);
        String htmlContent = templateEngine.process("emailSend.html", context);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("syimykjumabekuulu@gmail.com");
            helper.setTo(toEmail);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new NotFoundException("Email send failed!");
        } catch (NullPointerException e) {
            throw new NotFoundException("Not found and returned null!");
        }
    }
}
