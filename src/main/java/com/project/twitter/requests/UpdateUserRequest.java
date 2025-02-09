package com.project.twitter.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
