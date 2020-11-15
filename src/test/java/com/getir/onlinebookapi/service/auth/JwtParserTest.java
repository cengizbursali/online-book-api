package com.getir.onlinebookapi.service.auth;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JwtParserTest {

    @InjectMocks
    private JwtParser jwtParser;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(jwtParser, "secret", "my_secret");
    }

    @Test
    public void it_should_get_claims_from_token() {
        // Given
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6" +
                "ImNidXJzYWxpIiwidXNlcklkIjoxMjM0NX0.xOpVtF5_TB6qLAruPb0idwgRFno4OelCGTcl2xJQhHA";

        // When
        Claims claims = jwtParser.getClaims(jwtToken);

        // Then
        assertThat(claims.get("username")).isEqualTo("cbursali");
        assertThat(claims.get("userId")).isEqualTo(12345);
    }
}