package com.project.twitter.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TweetException extends RuntimeException{

    private final HttpStatus httpStatus;

    public TweetException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
