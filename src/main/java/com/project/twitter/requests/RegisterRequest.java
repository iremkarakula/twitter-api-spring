package com.project.twitter.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class RegisterRequest {

    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;

    private String username;

    private String contactInfo; //email or phone

    private String password;
}
