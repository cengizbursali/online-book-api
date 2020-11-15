package com.getir.onlinebookapi.service.auth;

import com.getir.onlinebookapi.configuration.JwtSecurityTokenConfig;
import com.getir.onlinebookapi.exception.AuthenticationFailedException;
import com.getir.onlinebookapi.model.User;
import com.getir.onlinebookapi.model.error.ErrorResponse;
import com.getir.onlinebookapi.utils.Clock;
import com.getir.onlinebookapi.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    public static final String BEARER = "Bearer";
    public static final String AUTHORIZATION = "Authorization";
    private static final long TOKEN_EXPIRATION_DAY_COUNT = 30L;

    private final JwtSecurityTokenConfig jwtSecurityTokenConfig;
    private final JwtParser jwtParser;

    @SneakyThrows
    public String createToken(User user) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("userName", user.getUserName());
        tokenData.put("id", user.getId());
        tokenData.put("email", user.getEmail());
        tokenData.put("firstName", user.getFirstName());
        tokenData.put("lastName", user.getLastName());
        tokenData.put("active", user.getActive());
        tokenData.put("admin", user.getAdmin());
        final LocalDateTime now = Clock.now();

        return Jwts.builder()
                .addClaims(tokenData)
                .setExpiration(DateUtils.convertLocalDateTimeToDate(now.plusDays(TOKEN_EXPIRATION_DAY_COUNT)))
                .setIssuedAt(DateUtils.convertLocalDateTimeToDate(now))
                .compressWith(CompressionCodecs.GZIP)
                .signWith(SignatureAlgorithm.HS512, jwtSecurityTokenConfig.getSecretKeySpec()).compact();
    }

    public Claims getTokenClaims(HttpServletRequest httpServletRequest) {
        return jwtParser.getClaims(getTokenFromHeader(httpServletRequest));
    }

    private String getTokenFromHeader(HttpServletRequest httpServletRequest) {
        String authenticationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        boolean startWithBearer = StringUtils.startsWith(authenticationHeader, BEARER);
        String[] headerParams = StringUtils.split(authenticationHeader, StringUtils.SPACE);
        boolean headerParamSizeIsTwo = ArrayUtils.getLength(headerParams) == 2;
        boolean isAuthenticationHeaderValid = authenticationHeader != null && startWithBearer && headerParamSizeIsTwo;
        if (!isAuthenticationHeaderValid) {
            throw new AuthenticationFailedException(UNAUTHORIZED, ErrorResponse.builder().error("Authentication header not valid").build());
        }
        return authenticationHeader.split(StringUtils.SPACE)[1];
    }
}
