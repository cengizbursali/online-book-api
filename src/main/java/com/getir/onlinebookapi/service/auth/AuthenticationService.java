package com.getir.onlinebookapi.service.auth;

import com.getir.onlinebookapi.converter.UserConverter;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.UserAuthentication;
import com.getir.onlinebookapi.model.request.AuthenticationRequest;
import com.getir.onlinebookapi.model.response.AuthenticationResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;
    private final UserConverter userConverter;

    public AuthenticationResponse createToken(AuthenticationRequest authenticationRequest) {
        // should request to user-api-client given username and password
        // then suppose return user as below.
        final User user = User.builder()
                .id(1)
                .email("admin@getir.com")
                .firstName("admin")
                .lastName("admin")
                .userName(authenticationRequest.getUsername())
                .active(true)
                .admin(true)
                .build();
        final String token = tokenService.createToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(token)
                .user(user)
                .build();
    }

    public Authentication getUserAuthentication(HttpServletRequest httpServletRequest) {
        final Claims claims = tokenService.getTokenClaims(httpServletRequest);
        return new UserAuthentication(userConverter.convert(claims));
    }
}
