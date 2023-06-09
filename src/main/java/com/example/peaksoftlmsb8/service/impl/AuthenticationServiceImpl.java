package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.authentication.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.request.authentication.PasswordRequest;
import com.example.peaksoftlmsb8.dto.response.authentication.AuthenticationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final JavaMailSender mailSender;
    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);

    @Override
    public AuthenticationResponse sigIn(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
                    logger.error("User with email: " + request.getEmail() + " not found!");
                 throw    new NotFoundException("Пользователь с электронной почтой: " + request.getEmail() + " не найден!");});
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            logger.error("Wrong password!");
            throw new BadRequestException("Неправильный пароль!");
        }
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }

    @Override
    public SimpleResponse forgotPassword(String email, String link) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("This email : " + email + " is not found !");
                    throw  new NotFoundException("Пользователь с электронной почтой : " + email + " не найден!");});

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setSubject("Password reset request");
        mimeMessageHelper.setFrom("peaksoftLMS@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(link + "/" + user.getId(), true);
        mailSender.send(mimeMessage);
        logger.info("SMS sent to mail !");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SMS отправлено на почту!")
                .build();
    }

    @Override
    public SimpleResponse resetPassword(PasswordRequest passwordRequest) {
        User user = userRepository.findById(passwordRequest.getId())
                .orElseThrow(() -> {
                    logger.error("This id : " + passwordRequest.getId() + " is not found !");
                    throw new NotFoundException("Этот идентификатор: " + passwordRequest.getId() + " не найден!");});
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        logger.info("Password successfully updated");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пароль успешно обновлен")
                .build();
    }
}
