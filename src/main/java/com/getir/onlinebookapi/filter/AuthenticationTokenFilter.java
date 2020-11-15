package com.getir.onlinebookapi.filter;

import com.getir.onlinebookapi.exception.AuthenticationFailedException;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends GenericFilterBean {

    private final AuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        try {
            Authentication userAuthentication = authenticationService.getUserAuthentication(httpRequest);
            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        } catch (AuthenticationFailedException e) {
            handleSecurityError((HttpServletResponse) servletResponse, "Authentication header not valid.");
            return;
        } catch (MalformedJwtException m) {
            handleSecurityError((HttpServletResponse) servletResponse, "JWT was not correctly constructed.");
            return;
        } catch (ExpiredJwtException e) {
            handleSecurityError((HttpServletResponse) servletResponse, "Token is expired.");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void handleSecurityError(HttpServletResponse httpServletResponse, String message) throws IOException {
        SecurityContextHolder.clearContext();
        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), message);
    }
}