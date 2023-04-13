package com.example.peaksoftlmsb8.api;

import com.example.peaksoftlmsb8.dto.request.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.response.AuthenticationResponse;
import com.example.peaksoftlmsb8.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationApi {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest authenticationRequest
            ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
