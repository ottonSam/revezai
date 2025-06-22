package com.ottonsam.revezai.modules.user.entites;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name = "user_accounts")
@Data
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "\\S+", message = "Username cannot be spaces")
    private String username;  

    @Email(message = "Email should be valid")
    private String email;

    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
