package com.example.twitter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LikeException extends RuntimeException{

    private HttpStatus status;

    public LikeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
