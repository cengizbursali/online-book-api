package com.getir.onlinebookapi.service.auth;

import com.getir.onlinebookapi.configuration.JwtSecurityTokenConfig;
import com.getir.onlinebookapi.exception.AuthenticationFailedException;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.utils.Clock;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private JwtSecurityTokenConfig jwtSecurityTokenConfig;

    @Mock
    private JwtParser jwtParser;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    public void it_should_create_token() {
        //Given
        LocalDateTime createdDate = LocalDateTime.of(2020, Month.APRIL, 12, 23, 30, 00);
        Clock.freeze(createdDate);
        final User user = User.builder()
                .id(123123)
                .email("email@email.com")
                .firstName("firstname")
                .active(true)
                .lastName("lastname")
                .userName("email")
                .build();
        final SecretKeySpec secretKeySpec = new SecretKeySpec("a863e1cea691d4fb38f2578ff1ddf206".getBytes(StandardCharsets.UTF_8),
                                                              SignatureAlgorithm.HS512.getJcaName());
        when(jwtSecurityTokenConfig.getSecretKeySpec()).thenReturn(secretKeySpec);

        //When
        final String token = tokenService.createToken(user);

        //Then
        assertThat(token).isEqualTo("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAD2MXQqEMAyE75LnImr97ZMn8A5BuxBoq9RWhG" +
                                            "XvbqplISQzX5j5wof8EWa0GtSrXdICDP5xkpniEuhkFnzU7FZLDpSLxgigFVRVSx4B8dA-h7VFMpx8b_b" +
                                            "Ts4tls-lz7Zxsh1FWbVOW3IThAV1fSwa_G7LhkiilAAAA.WiFc2QIOj5ScXPFj7JYu-yDb48c7HqXZXbS" +
                                            "JZEE5s2pM1FR3OpOhLQ_sSZDxUM-KxSf164ZRsGkZSfCbtjl6Kw");
        Clock.unfreeze();
    }

    @Test
    public void it_should_throw_authenticationFailedException_when_token_not_contains_authorization_header() {
        //when
        AuthenticationFailedException exception = (AuthenticationFailedException) catchThrowable(() -> tokenService.getTokenClaims(httpServletRequest));

        //then
        assertThat(exception.getErrorResponse().getErrors()).containsExactly("Authentication header not valid");
    }

    @Test
    public void it_should_throw_authenticationFailedException_when_token_not_contains_bearer() {
        //given
        given(httpServletRequest.getHeader("Authorization")).willReturn("Como");

        //when
        AuthenticationFailedException exception = (AuthenticationFailedException) catchThrowable(() -> tokenService.getTokenClaims(httpServletRequest));

        //then
        assertThat(exception.getErrorResponse().getErrors()).containsExactly("Authentication header not valid");
    }

    @Test
    public void it_should_throw_authenticationFailedException_when_token_contains_invalid_header() {
        //given
        given(httpServletRequest.getHeader("Authorization")).willReturn("Bearer");

        //when
        AuthenticationFailedException exception = (AuthenticationFailedException) catchThrowable(() -> tokenService.getTokenClaims(httpServletRequest));

        //then
        assertThat(exception.getErrorResponse().getErrors()).containsExactly("Authentication header not valid");
    }

    @Test
    public void it_should_throw_exception_when_token_expired() {
        //given
        given(httpServletRequest.getHeader("Authorization")).willReturn("Bearer token");
        ExpiredJwtException mockEx = mock(ExpiredJwtException.class);
        given(jwtParser.getClaims("token")).willThrow(mockEx);

        //when
        ExpiredJwtException exception = (ExpiredJwtException) catchThrowable(() -> tokenService.getTokenClaims(httpServletRequest));

        //then
        assertThat(exception).isEqualTo(mockEx);
    }
}