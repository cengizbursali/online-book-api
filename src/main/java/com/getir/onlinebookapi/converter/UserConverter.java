package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.model.User;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User convert(Claims claims) {
        return User.builder()
                .id(getIntegerValue(claims, "id"))
                .userName(getStringValue(claims, "userName"))
                .lastName(getStringValue(claims, "lastName"))
                .firstName(getStringValue(claims, "firstName"))
                .email(getStringValue(claims, "email"))
                .active(getBooleanValue(claims, "active"))
                .admin(getBooleanValue(claims, "admin"))
                .build();
    }

    private String getStringValue(Claims claims, String key) {
        final Object foundValue = claims.getOrDefault(key, StringUtils.EMPTY);
        return foundValue == null ? null : foundValue.toString();
    }

    private Integer getIntegerValue(Claims claims, String key) {
        final String value = getStringValue(claims, key);
        return StringUtils.isNotBlank(value) ? Integer.parseInt(value) : 0;
    }

    private Boolean getBooleanValue(Claims claims, String key) {
        final String value = getStringValue(claims, key);
        return StringUtils.isNotBlank(value) && Boolean.parseBoolean(value);
    }
}
