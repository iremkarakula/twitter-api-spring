package com.project.twitter.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {

    private Integer status;
    private String message;
    private Long timestamp;



}