package com.example.twitter.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "authentication")
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "token")
    @NotNull(message = "Token can not be null.")
    private String token;

    @Column(name = "expires_at")
    @NotNull(message = "Expire date can not be null.")
    private LocalDateTime expiresAt;
}
