package com.project.twitter.service;

import com.project.twitter.responses.GuestResponse;

import java.util.Set;

public interface FollowService {

    void follow(String followerUsername, String followingUsername);
    void unfollow(String followerUsername, String followingUsername);
    Set<GuestResponse> getFollowers(String username);
    Set<GuestResponse> getFollowings(String username);


}
