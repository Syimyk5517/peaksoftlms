package com.example.peaksoftlmsb8.service.impl;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.db.exception.BadCredentialException;
import com.example.peaksoftlmsb8.dto.request.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.response.AuthenticationResponse;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager manager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;

    @Override
    public AuthenticationResponse sigIn(AuthenticationRequest request) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new BadCredentialException("Wrong data")
        );
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }

}
