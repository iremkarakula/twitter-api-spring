package com.project.twitter.service.Impl;

import com.project.twitter.entity.User;
import com.project.twitter.exceptions.UserException;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.responses.GuestResponse;
import com.project.twitter.service.FollowService;
import com.project.twitter.util.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;

    @Override
    public void follow(String followerUsername, String followingUsername) {

        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new UserException("Takip eden kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new UserException("Takip edilen kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        if (follower.getFollowings().contains(following)) {
            throw new UserException("Zaten bu kullanıcıyı takip ediyorsunuz", HttpStatus.BAD_REQUEST);
        }

        follower.getFollowings().add(following);
        following.getFollowers().add(follower);

        userRepository.save(follower);
        userRepository.save(following);

    }

    @Override
    public void unfollow(String followerUsername, String followingUsername) {

        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new UserException("Takip eden kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new UserException("Takip edilen kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        if (!follower.getFollowings().contains(following)) {
            throw new UserException("Bu kullanıcıyı takip etmiyorsunuz", HttpStatus.BAD_REQUEST);
        }

        follower.getFollowings().remove(following);
        following.getFollowers().remove(follower);

        userRepository.save(follower);
        userRepository.save(following);

    }

    @Override
    public Set<GuestResponse> getFollowers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        return user.getFollowers().stream()
                .map(u -> UserMapper.toGuestResponse(u))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GuestResponse> getFollowings(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        return user.getFollowings().stream()
                .map(u -> UserMapper.toGuestResponse(u))
                .collect(Collectors.toSet());
    }
}
