package com.example.peaksoftlmsb8.service.impl;
import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.exception.BadRequestException;
import com.example.peaksoftlmsb8.db.exception.NotFoundException;
import com.example.peaksoftlmsb8.dto.request.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.request.PasswordRequest;
import com.example.peaksoftlmsb8.dto.response.AuthenticationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @Override
    public AuthenticationResponse sigIn(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()->  new NotFoundException("User with email: " + request.getEmail() + " not found!")
        );
        if (!encoder.matches(request.getPassword(), user.getPassword())){
            throw new BadRequestException("Wrong password!");
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
                .orElseThrow(() -> new NotFoundException("This email : " + email + " is not found !"));
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setSubject("Password reset request");
        mimeMessageHelper.setFrom("peaksoftLMS@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(link + "/" + user.getId(), true);
        mailSender.send(mimeMessage);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SMS sent to mail !")
                .build();
    }

    @Override
    public SimpleResponse resetPassword(PasswordRequest passwordRequest) {
        User user = userRepository.findById(passwordRequest.getId())
                .orElseThrow(() -> new NotFoundException("This id : " + passwordRequest.getId() + " is not found !"));
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Password successfully updated")
                .build();
    }
}
