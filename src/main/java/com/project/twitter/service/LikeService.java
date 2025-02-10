package com.project.twitter.service;

public interface LikeService {

    void addTweetLike(long tweetId, String username);
    void removeTweetLike(long tweetId, String username);
    void addCommentLike(long commentId, String username);
    void removeCommentLike(long commentId, String username);
    int getTweetLikeCount(long tweetId);
    int getCommentLikeCount(long commentId);


}
