package com.project.twitter.controller;

import com.project.twitter.service.LikeService;
import com.project.twitter.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RequestMapping("/likes")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/tweet/{tweetId}/like")
    public ResponseEntity<String> addTweetLike(@PathVariable long tweetId) {

        String authenticatedUsername = AuthUtil.getAuthenticatedUsername();
        likeService.addTweetLike(tweetId, authenticatedUsername);
        return new ResponseEntity<>("Tweet beğenildi", HttpStatus.OK);
    }


    @DeleteMapping("/tweet/{tweetId}/like")
    public ResponseEntity<String> removeTweetLike(@PathVariable long tweetId) {
        String authenticatedUsername = AuthUtil.getAuthenticatedUsername();
        likeService.removeTweetLike(tweetId, authenticatedUsername);
        return new ResponseEntity<>("Tweet beğenisi kaldırıldı", HttpStatus.OK);
    }


    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<String> addCommentLike(@PathVariable long commentId) {

        String authenticatedUsername = AuthUtil.getAuthenticatedUsername();
        likeService.addCommentLike(commentId, authenticatedUsername);
        return new ResponseEntity<>("Yorum beğenildi", HttpStatus.OK);
    }


    @DeleteMapping("/comment/{commentId}/like")
    public ResponseEntity<String> removeCommentLike(@PathVariable long commentId, @RequestParam String username) {

        String authenticatedUsername = AuthUtil.getAuthenticatedUsername();
        likeService.removeCommentLike(commentId, authenticatedUsername);
        return new ResponseEntity<>("Yorum beğenisi kaldırıldı.", HttpStatus.OK);
    }


    @GetMapping("/tweet/{tweetId}/count")
    public ResponseEntity<Integer> getTweetLikeCount(@PathVariable long tweetId) {

        int likeCount = likeService.getTweetLikeCount(tweetId);
        return new ResponseEntity<>(likeCount, HttpStatus.OK);
    }


    @GetMapping("/comment/{commentId}/count")
    public ResponseEntity<Integer> getCommentLikeCount(@PathVariable long commentId) {

        int likeCount = likeService.getCommentLikeCount(commentId);
        return new ResponseEntity<>(likeCount, HttpStatus.OK);
    }
}
