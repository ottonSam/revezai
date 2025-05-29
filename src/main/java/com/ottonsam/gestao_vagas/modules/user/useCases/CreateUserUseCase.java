package com.ottonsam.gestao_vagas.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ottonsam.gestao_vagas.exceptions.UserFoundException;
import com.ottonsam.gestao_vagas.modules.user.entites.UserEntity;
import com.ottonsam.gestao_vagas.modules.user.repositories.UserRepository;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity execute(UserEntity user) {
        this.userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
            .ifPresent(c -> {
                throw new UserFoundException();
            });
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }
}
