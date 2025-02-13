package com.example.twitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserException userException){
        ErrorResponse errorResponse = new ErrorResponse(
                userException.getMessage(),
                userException.getStatus().value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, userException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(TweetException tweetException){
        ErrorResponse errorResponse = new ErrorResponse(
                tweetException.getMessage(),
                tweetException.getStatus().value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, tweetException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RetweetException retweetException){
        ErrorResponse errorResponse = new ErrorResponse(
                retweetException.getMessage(),
                retweetException.getStatus().value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, retweetException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(LikeException likeException){
        ErrorResponse errorResponse = new ErrorResponse(
                likeException.getMessage(),
                likeException.getStatus().value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, likeException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CommentException commentException){
        ErrorResponse errorResponse = new ErrorResponse(
                commentException.getMessage(),
                commentException.getStatus().value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, commentException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
