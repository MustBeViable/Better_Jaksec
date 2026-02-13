package com.api.common.util;

import com.api.common.error.exceptions.UnauthorizedException;
import com.api.login.Auth;
import com.api.login.User;
import com.api.login.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class JwtUtils {

    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    public static String tokenize(UserDto user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .signWith(key)
                .compact();
    }

    public static String parse(String token){
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token");
        }
    }
    public static Claims parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    public static Auth toAuth(String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }else {
            throw new UnauthorizedException("Invalid token");
        }
        Claims claims = parseClaims(token);
        return new Auth(claims.get("role", String.class),
                claims.getSubject());
    }
}
