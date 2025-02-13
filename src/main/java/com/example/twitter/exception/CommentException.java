package com.example.twitter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommentException extends RuntimeException{

    private HttpStatus status;

    public CommentException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
