package com.project.twitter.service;

public interface RepostService {

    void createRepost();
    void deleteRepost();
    void getAllRepost();
    void getAllRepostByTweetId();
    void getAllRepostByUsername();
}
