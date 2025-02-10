package com.project.twitter.controller;


import com.project.twitter.responses.GuestResponse;
import com.project.twitter.service.FollowService;
import com.project.twitter.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followingUsername}")
    public ResponseEntity<String> followUser(@PathVariable String followingUsername) {
        String authenticatedUsername = AuthUtil.getAuthenticatedUsername();
        followService.follow(authenticatedUsername, followingUsername);
        return ResponseEntity.ok("Takip işlemi başarılı");
    }

    @DeleteMapping("/{followingUsername}")
    public ResponseEntity<String> unfollowUser(@PathVariable String followingUsername) {
        String authenticatedUsername = AuthUtil.getAuthenticatedUsername();
        followService.unfollow(authenticatedUsername, followingUsername);
        return ResponseEntity.ok("Takipten çıkıldı");
    }

    @GetMapping("/followers")
    public ResponseEntity<List<GuestResponse>> getFollowers() {
        String authenticatedUsername = AuthUtil.getAuthenticatedUsername();
        return ResponseEntity.ok(followService.getFollowers(authenticatedUsername)
                .stream()
                .sorted((x,y) -> y.getRecordTime().compareTo(x.getRecordTime()))
                .toList());
    }

    @GetMapping("/followings")
    public ResponseEntity<List<GuestResponse>> getFollowings() {
        String authenticatedUsername = AuthUtil.getAuthenticatedUsername();
        return ResponseEntity.ok(followService.getFollowings(authenticatedUsername)
                .stream()
                .sorted((x,y) -> y.getRecordTime().compareTo(x.getRecordTime()))
                .toList());
    }


}
