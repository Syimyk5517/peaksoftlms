package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.authentication.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.request.authentication.PasswordRequest;
import com.example.peaksoftlmsb8.dto.response.authentication.AuthenticationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    AuthenticationResponse sigIn(AuthenticationRequest request);

    SimpleResponse forgotPassword(String email, String link) throws MessagingException;

    SimpleResponse resetPassword(PasswordRequest passwordRequest);
}
