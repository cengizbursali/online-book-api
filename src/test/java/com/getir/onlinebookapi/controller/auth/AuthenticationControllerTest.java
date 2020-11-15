package com.getir.onlinebookapi.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.onlinebookapi.model.request.AuthenticationRequest;
import com.getir.onlinebookapi.model.response.AuthenticationResponse;
import com.getir.onlinebookapi.service.auth.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void it_should_get_token() throws Exception {
        //given
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        given(authenticationService.createToken(any(AuthenticationRequest.class))).willReturn(authenticationResponse);

        //when
        ResultActions resultActions = mockMvc.perform(post("/auth")
                                                              .contentType("application/json")
                                                              .content("{\n" +
                                                                               "  \"username\": \"username\",\n" +
                                                                               "  \"password\": \"password\"\n" +
                                                                               "}"));

        //then
        resultActions.andExpect(status().isOk());
        ArgumentCaptor<AuthenticationRequest> requestCaptor = ArgumentCaptor.forClass(AuthenticationRequest.class);
        verify(authenticationService).createToken(requestCaptor.capture());
        AuthenticationRequest authenticationRequest = requestCaptor.getValue();
        assertThat(authenticationRequest.getUsername()).isEqualTo("username");
        assertThat(authenticationRequest.getPassword()).isEqualTo("password");
    }

    @Test
    public void it_should_return_bad_request_when_username_is_blank() throws Exception {
        //given
        final AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username(" ")
                .password("password")
                .build();


        //when
        ResultActions resultActions = mockMvc.perform(post("/auth")
                                                              .contentType("application/json")
                                                              .content(new ObjectMapper().writeValueAsString(authenticationRequest)));

        //then
        resultActions.andExpect(status().isBadRequest());
        verifyNoInteractions(authenticationService);
    }
}