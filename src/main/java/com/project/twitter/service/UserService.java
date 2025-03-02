package com.project.twitter.service;

import com.project.twitter.entity.User;
import com.project.twitter.requests.UpdateUserRequest;
import com.project.twitter.responses.GuestResponse;
import com.project.twitter.responses.UserResponse;

import java.util.Set;


public interface UserService {

    UserResponse getUser(String username);
    GuestResponse getGuestUser(String username);
    User updateUser(String username, UpdateUserRequest request);
    Set<GuestResponse> searchUsersByInput(String input);

}
