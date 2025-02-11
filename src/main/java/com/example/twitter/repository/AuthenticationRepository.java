package com.example.twitter.repository;

import com.example.twitter.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Optional<Authentication> findByUserUserId(Long userId);

}
