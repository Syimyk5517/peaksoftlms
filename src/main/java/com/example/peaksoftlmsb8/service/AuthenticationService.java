package com.example.peaksoftlmsb8.service;

import com.example.peaksoftlmsb8.dto.request.AuthenticationRequest;
import com.example.peaksoftlmsb8.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse sigIn(AuthenticationRequest request);

}
