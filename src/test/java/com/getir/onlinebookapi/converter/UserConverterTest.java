package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {

    @InjectMocks
    private UserConverter userConverter;

    @Test
    public void it_should_convert() {
        //given
        Map<String, Object> claimsMap = new HashMap<>();
        Claims claims = new DefaultClaims(claimsMap);
        claimsMap.put("id", 930015);
        claimsMap.put("userName", "cbursali");
        claimsMap.put("lastName", "lastName");
        claimsMap.put("firstName", "firstName");
        claimsMap.put("email", "email");
        claimsMap.put("admin", true);
        claimsMap.put("active", false);

        //when
        User user = userConverter.convert(claims);

        //then
        assertThat(user.getId()).isEqualTo(930015);
        assertThat(user.getUserName()).isEqualTo("cbursali");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getFirstName()).isEqualTo("firstName");
        assertThat(user.getLastName()).isEqualTo("lastName");
        assertThat(user.getActive()).isFalse();
        assertThat(user.getAdmin()).isTrue();
    }

    @Test
    public void it_should_convert_when_user_id_and_username_is_blank() {
        //given
        Map<String, Object> claimsMap = new HashMap<>();
        Claims claims = new DefaultClaims(claimsMap);
        claimsMap.put("id", null);
        claimsMap.put("userName", null);

        //when
        User user = userConverter.convert(claims);

        //then
        assertThat(user.getId()).isEqualTo(0);
        assertThat(user.getEmail()).isEqualTo("");
    }


}