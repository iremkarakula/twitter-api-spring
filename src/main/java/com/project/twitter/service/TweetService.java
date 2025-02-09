package com.project.twitter.service;

import com.project.twitter.entity.Tweet;
import com.project.twitter.responses.TweetResponse;

import java.util.List;
import java.util.Set;

public interface TweetService {

    TweetResponse findTweetByTweetId(long tweetId);
    Set<TweetResponse> findAllTweets();
    Set<TweetResponse> findTweetsByUsername(String username);

    List<TweetResponse> sortTweetsByDateASC();
    List<TweetResponse> sortTweetsByDateDESC();
    List<TweetResponse> sortTweetsByCommentASC();
    List<TweetResponse> sortTweetsByLikeASC();
    List<TweetResponse> sortTweetsByEngagementASC();
    List<TweetResponse> sortTweetsByEngagementDESC();
    List<TweetResponse> searchTweetByInput(String input);

    TweetResponse createTweet(String text, String username);
    TweetResponse updateTweet(String text, long tweetId);
    void deleteTweet(long tweetId);


}
