package com.ottonsam.revezai.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTProvider {

    @Value("${security.token.secrete}")
    private String secreteToken;

    public String ValidateToken(String token) {
        token = token.replace("Bearer ", "");
        
        Algorithm algorithm = Algorithm.HMAC256(secreteToken);
        try {
            var subject = JWT.require(algorithm).build().verify(token).getSubject();
            return subject;
        } catch (JWTVerificationException e) {
            return "";
        }
    }
}
