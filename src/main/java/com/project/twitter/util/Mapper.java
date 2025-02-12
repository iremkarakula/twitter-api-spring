package com.project.twitter.util;

import com.project.twitter.entity.Comment;
import com.project.twitter.entity.Tweet;
import com.project.twitter.responses.CommentResponse;
import com.project.twitter.responses.TweetResponse;


public class Mapper {
    public static TweetResponse toTweetResponse(Tweet tweet) {
        return new TweetResponse(
                tweet.getId(),
                tweet.getUser().getName(),
                tweet.getUser().getUsername(),
                tweet.getText(),
                tweet.getRecordTime(),
                tweet.getComments().size(),
                tweet.getLikes().stream().map(l -> l.getUser().getUsername()).toList(),
                tweet.getReposts().size()
        );
    }

    public static CommentResponse toCommentResponse(Comment comment){
        TweetResponse tweetResponse = toTweetResponse(comment.getTweet());
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getName(),
                comment.getUser().getUsername(),
                comment.getText(),
                tweetResponse,
                comment.getRecordTime(),
                comment.getLikes().size(),
                comment.getReposts().size()
        );
    }
}
