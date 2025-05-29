package com.ottonsam.gestao_vagas.modules.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ottonsam.gestao_vagas.modules.user.dto.AuthUserDTO;
import com.ottonsam.gestao_vagas.modules.user.entites.UserEntity;
import com.ottonsam.gestao_vagas.modules.user.useCases.AuthUserUseCase;
import com.ottonsam.gestao_vagas.modules.user.useCases.CreateUserUseCase;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private AuthUserUseCase authUserUseCase;
    
    @PostMapping("")
    public ResponseEntity<Object> create(@Valid @RequestBody UserEntity company) {
        try {
            var result = this.createUserUseCase.execute(company);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthUserDTO auth) {
        try {
            var result = this.authUserUseCase.execute(auth);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
