package com.getir.onlinebookapi.filter;

import com.getir.onlinebookapi.exception.AuthenticationFailedException;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.UserAuthentication;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationTokenFilterTest {

    @InjectMocks
    private AuthenticationTokenFilter authenticationTokenFilter;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Mock
    private FilterChain filterChain;

    @Test
    public void it_should_authenticate_user() throws IOException, ServletException {
        //given

        UserAuthentication userAuthentication = new UserAuthentication(User.builder().build());
        given(authenticationService.getUserAuthentication(mockHttpServletRequest)).willReturn(userAuthentication);

        //when
        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, filterChain);

        //then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(userAuthentication);
        verify(filterChain).doFilter(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void it_should_return_401_when_throw_authentication_exception() throws IOException, ServletException {
        //given
        given(authenticationService.getUserAuthentication(mockHttpServletRequest)).willThrow(AuthenticationFailedException.class);

        //when
        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, filterChain);

        //then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(null);
        verifyNoInteractions(filterChain);
    }

    @Test
    public void it_should_return_401_when_throw_MalformedJwtException_exception() throws IOException, ServletException {
        //given
        given(authenticationService.getUserAuthentication(mockHttpServletRequest)).willThrow(MalformedJwtException.class);

        //when
        authenticationTokenFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, filterChain);

        //then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(null);
        verifyNoInteractions(filterChain);
    }
}