package com.project.twitter.service;

import com.project.twitter.responses.RepostResponse;

import java.util.List;

public interface RepostService {

    RepostResponse createRepost(long tweetId, String username, String text);
    void deleteRepost(long repostId);
    List<RepostResponse> getAllRepost();
    List<RepostResponse> getAllRepostByTweetId(long tweetId);
    List<RepostResponse> getAllRepostByUsername(String username);
}
