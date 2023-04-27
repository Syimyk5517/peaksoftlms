package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.request.PasswordRequest;
import com.example.peaksoftlmsb8.dto.response.AuthenticationResponse;
import com.example.peaksoftlmsb8.dto.response.SimpleResponse;
import com.example.peaksoftlmsb8.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationApi {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.sigIn(authenticationRequest));
    }
    @PostMapping("/forgot_password")
    public SimpleResponse forgotPassword(@RequestParam String email,
                                         @RequestParam String link) throws MessagingException {
        return authenticationService.forgotPassword(email, link);
    }
    @PostMapping("/reset_password")
    public SimpleResponse resetPassword(@RequestParam PasswordRequest newPassword){
        return authenticationService.resetPassword(newPassword);
    }
}
