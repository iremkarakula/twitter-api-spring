package com.project.twitter.controller;

import com.project.twitter.responses.RepostResponse;
import com.project.twitter.service.RepostService;
import com.project.twitter.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/repost")
@RestController
public class RepostController {

    private final RepostService repostService;

    @PostMapping("/create/{tweetId}")
    public ResponseEntity<RepostResponse> createRepost(@PathVariable long tweetId,
                                                       @RequestBody String text) {
        String authUsername = AuthUtil.getAuthenticatedUsername();
        if (authUsername == null) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı");
        }
        RepostResponse repostResponse = repostService.createRepost(tweetId, authUsername, text);
        return new ResponseEntity<>(repostResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{repostId}")
    public ResponseEntity<String> deleteRepost(@PathVariable long repostId) {
        repostService.deleteRepost(repostId);
        return new ResponseEntity<>("Repost başarıyla silindi.", HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<RepostResponse>> getAllReposts() {
        List<RepostResponse> reposts = repostService.getAllRepost();
        return new ResponseEntity<>(reposts, HttpStatus.OK);
    }

    @GetMapping("/tweet/{tweetId}")
    public ResponseEntity<List<RepostResponse>> getRepostsByTweetId(@PathVariable long tweetId) {
        List<RepostResponse> reposts = repostService.getAllRepostByTweetId(tweetId);
        return new ResponseEntity<>(reposts, HttpStatus.OK);
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<List<RepostResponse>> getRepostsByUsername() {
        String authUsername = AuthUtil.getAuthenticatedUsername();
        List<RepostResponse> reposts = repostService.getAllRepostByUsername(authUsername);
        return new ResponseEntity<>(reposts, HttpStatus.OK);
    }


}
