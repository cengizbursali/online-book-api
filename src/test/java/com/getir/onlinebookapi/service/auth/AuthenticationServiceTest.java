package com.getir.onlinebookapi.service.auth;

import com.getir.onlinebookapi.converter.UserConverter;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.UserAuthentication;
import com.getir.onlinebookapi.model.request.AuthenticationRequest;
import com.getir.onlinebookapi.model.response.AuthenticationResponse;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserConverter userConverter;

    @Test
    public void it_should_create_token() {
        // Given
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username("username")
                .password("password")
                .build();

        when(tokenService.createToken(any(User.class))).thenReturn("valid-token");

        // When
        AuthenticationResponse authenticationResponse = authenticationService.createToken(authenticationRequest);

        // Then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(tokenService).createToken(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("admin@getir.com");
        assertThat(user.getFirstName()).isEqualTo("admin");
        assertThat(user.getLastName()).isEqualTo("admin");
        assertThat(user.getUserName()).isEqualTo("username");
        assertThat(user.getActive()).isTrue();
        assertThat(user.getAdmin()).isTrue();

        assertThat(authenticationResponse.getJwtToken()).isEqualTo("valid-token");
        assertThat(authenticationResponse.getUser()).isEqualTo(user);
    }

    @Test
    public void it_should_get_authentication() {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        final User user = new User();
        user.setUserName("cengizbursali");
        given(tokenService.getTokenClaims(httpServletRequest)).willReturn(claims);
        given(userConverter.convert(claims)).willReturn(user);

        //when
        UserAuthentication userAuthentication = (UserAuthentication) authenticationService.getUserAuthentication(httpServletRequest);

        //then
        assertThat(userAuthentication.getDetails()).isEqualTo(user);
        assertThat(userAuthentication.getPrincipal()).isEqualTo(user);
        assertThat(userAuthentication.getName()).isEqualTo("cengizbursali");
    }
}