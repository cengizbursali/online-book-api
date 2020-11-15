package com.getir.onlinebookapi.configuration;

import com.getir.onlinebookapi.filter.AuthenticationTokenFilter;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AuthenticationTokenFilter(authenticationService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        List<String> systemIgnoreList = Arrays.asList(
                "/swagger-ui.html",
                "/webjars/**",
                "/v2/**",
                "/swagger/**",
                "/swagger-resources/**",
                "/actuator/**",
                "/favicon.ico",
                "/csrf",
                "/",
                "/auth",
                "/tests/**"
        );
        webSecurity.ignoring().antMatchers(systemIgnoreList.toArray(new String[]{}));
        webSecurity.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}