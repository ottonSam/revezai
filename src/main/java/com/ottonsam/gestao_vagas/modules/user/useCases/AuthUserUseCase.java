package com.ottonsam.gestao_vagas.modules.user.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ottonsam.gestao_vagas.modules.user.dto.AuthUserDTO;
import com.ottonsam.gestao_vagas.modules.user.dto.AuthUserResponseDTO;
import com.ottonsam.gestao_vagas.modules.user.repositories.UserRepository;

@Service
public class AuthUserUseCase {

    @Value("${security.token.secrete}")
    private String secreteToken;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthUserResponseDTO execute(AuthUserDTO auth) throws AuthenticationException {
        var user = this.userRepository.findByUsername(auth.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException("User or password incorrect");
        });

        if (!this.passwordEncoder.matches(auth.getPassword(), user.getPassword())) {
            throw new AuthenticationException("User or password incorrect");
        }

        Algorithm algorithm = Algorithm.HMAC256(secreteToken);
        
        var token = JWT.create().withIssuer("revezai")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .withSubject(user.getId().toString())
            .withClaim("roles", Arrays.asList("user"))
            .sign(algorithm);
    
        return AuthUserResponseDTO.builder()
            .accessToken(token)
            .build();
    }
}
