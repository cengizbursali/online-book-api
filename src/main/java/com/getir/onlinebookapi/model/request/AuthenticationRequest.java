package com.getir.onlinebookapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "authenticationRequest.username.notBlank")
    private String username;

    @NotBlank(message = "authenticationRequest.password.notBlank")
    private String password;
}
