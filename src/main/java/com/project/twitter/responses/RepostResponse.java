package com.project.twitter.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class RepostResponse {

    private long id;
    private String name;
    private String username;
    private String text;
    private TweetResponse tweetResponse;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date recordTime;
}
