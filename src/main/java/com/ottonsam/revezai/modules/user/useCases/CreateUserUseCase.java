package com.ottonsam.revezai.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ottonsam.revezai.exceptions.UserFoundException;
import com.ottonsam.revezai.modules.user.dto.UserResponseDTO;
import com.ottonsam.revezai.modules.user.entites.UserEntity;
import com.ottonsam.revezai.modules.user.repositories.UserRepository;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(UserEntity user) {
        this.userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
            .ifPresent(c -> {
                throw new UserFoundException();
            });
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return new UserResponseDTO(this.userRepository.save(user));
    }
}
