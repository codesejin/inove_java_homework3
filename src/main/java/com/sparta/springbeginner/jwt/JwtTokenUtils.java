package com.sparta.springbeginner.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sparta.springbeginner.security.UserDetailsImpl;

import java.util.Date;

public final class JwtTokenUtils {

    private static final int ACCESS_TOKEN_EXPIRE_TIME = 1000 * 30; // 30초
    private static final int REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 5; // 5분

    // Access Token 생성
    public static String generateJwtToken(UserDetailsImpl userDetails) {
        return JWT.create()
                .withIssuer("Alice")
                .withClaim("EXPIRATION_TIME", new Date(System.currentTimeMillis()+ACCESS_TOKEN_EXPIRE_TIME))
                .withClaim("USERNAME", userDetails.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
    }

    // Refresh Token 생성
    public static String generateREJwtToken(UserDetailsImpl userDetails) {

        return JWT.create()
                .withIssuer("Alice")
                .withClaim("USERNAME", userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
    }
}