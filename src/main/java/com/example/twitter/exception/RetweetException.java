package com.example.twitter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RetweetException extends RuntimeException{

    private HttpStatus status;

    public RetweetException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
