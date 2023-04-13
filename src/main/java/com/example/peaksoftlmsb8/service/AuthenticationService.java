package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.config.JwtService;
import com.example.peaksoftlmsb8.db.entity.User;
import com.example.peaksoftlmsb8.repository.UserRepository;
import com.example.peaksoftlmsb8.dto.request.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager manager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
