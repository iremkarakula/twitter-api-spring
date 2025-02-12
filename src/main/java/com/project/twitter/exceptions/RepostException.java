package com.project.twitter.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RepostException extends RuntimeException{

    private final HttpStatus httpStatus;

    public RepostException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
