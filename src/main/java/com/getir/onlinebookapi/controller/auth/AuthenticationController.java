package com.getir.onlinebookapi.controller.auth;

import com.getir.onlinebookapi.model.request.AuthenticationRequest;
import com.getir.onlinebookapi.model.response.AuthenticationResponse;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public AuthenticationResponse createToken(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return authenticationService.createToken(authenticationRequest);
    }
}
