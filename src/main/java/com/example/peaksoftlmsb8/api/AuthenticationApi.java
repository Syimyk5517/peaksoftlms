package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.authentication.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.request.authentication.ForgotPasswordRequest;
import com.example.peaksoftlmsb8.dto.request.authentication.PasswordRequest;
import com.example.peaksoftlmsb8.dto.response.authentication.AuthenticationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@Tag(name = "Authentications")
public class  AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.sigIn(authenticationRequest));
    }

    @PostMapping("/forgot_password")
    public SimpleResponse forgotPassword(@RequestBody @Valid ForgotPasswordRequest passwordRequest) throws MessagingException {
        return authenticationService.forgotPassword(passwordRequest);
    }

    @PostMapping("/reset_password")
    public SimpleResponse resetPassword(@RequestBody @Valid PasswordRequest newPassword) {
        return authenticationService.resetPassword(newPassword);
    }

}
