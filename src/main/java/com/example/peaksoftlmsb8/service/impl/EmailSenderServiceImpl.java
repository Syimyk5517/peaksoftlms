package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(EmailSenderServiceImpl.class);

    @Override
    public void emailSender(String toEmail, String link) {
        User user = userRepository.findByEmail(toEmail).orElseThrow(
                () -> {
                    logger.error("User with email : " + toEmail + " not found");
                    throw  new NotFoundException("Пользователь с электронной почтой: " + toEmail + " не найден!");
                });
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
            logger.error("Sending email failed!");
            throw new NotFoundException("Отправка электронной почты не удалась!");
        } catch (NullPointerException e) {
            logger.error("Not found and returned null!");
            throw new NotFoundException("Не найдено и возвращено значение null!");
        }
    }
}
