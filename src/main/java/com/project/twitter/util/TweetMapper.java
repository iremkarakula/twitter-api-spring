package com.project.twitter.util;

import com.project.twitter.entity.Tweet;
import com.project.twitter.responses.TweetResponse;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

    public TweetResponse toTweetResponse(Tweet tweet) {
        return new TweetResponse(
                tweet.getId(),
                tweet.getUser().getName(),
                tweet.getUser().getUsername(),
                tweet.getText(),
                tweet.getRecordTime(),
                tweet.getComments().size(),
                tweet.getLikes().size(),
                tweet.getReposts().size()
        );
    }
}
