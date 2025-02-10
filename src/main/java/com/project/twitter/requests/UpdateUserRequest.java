package com.project.twitter.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class UpdateUserRequest {

    private String name;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    private String email;
    private String phoneNumber;
    private String bio;
    private String website;
    private String location;

}
