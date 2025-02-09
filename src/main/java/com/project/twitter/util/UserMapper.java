package com.project.twitter.util;

import com.project.twitter.entity.User;
import com.project.twitter.responses.GuestResponse;
import com.project.twitter.responses.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public GuestResponse toGuestResponse(User user){
        return  new GuestResponse(
                user.getName(),
                user.getUsername(),
                user.getRecordTime(),
                false
        );
    }

    public UserResponse toUserResponse(User user){
        return  new UserResponse(user.getName(),
                user.getUsername(),
                user.getBirthDate(),
                user.getRecordTime(),
                true,
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBio(),
                user.getWebsite(),
                user.getLocation());
    }
}
