package com.example.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true)
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @NotNull(message = "Username can not be null")
    @NotBlank(message = "Username can not be empty")
    private String username;

    @Column(name = "password")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    @NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be empty")
    private String password;

    @Column(name = "email", unique = true)
    @Size(min = 7, max = 100, message = "Email must be between 7 and 100 characters")
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
