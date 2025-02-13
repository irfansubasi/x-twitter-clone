package com.example.twitter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends RuntimeException{

    private HttpStatus status;

    public UnauthorizedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
