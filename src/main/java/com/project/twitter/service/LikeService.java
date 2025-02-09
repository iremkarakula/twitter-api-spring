package com.project.twitter.service;

public interface LikeService {

    boolean addTweetLike(long tweetId);
    boolean removeTweetLike(long tweetId);
    boolean addCommentLike(long commentId);
    boolean removeCommentLike(long commentId);
    int getTweetLikeCount(long tweetId);
    int getCommentLikeCount(long commentId);


}
