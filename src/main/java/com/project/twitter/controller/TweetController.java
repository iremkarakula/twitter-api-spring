package com.project.twitter.controller;

import com.project.twitter.entity.Tweet;
import com.project.twitter.exceptions.TweetException;
import com.project.twitter.exceptions.UserException;
import com.project.twitter.repository.TweetRepository;
import com.project.twitter.responses.TweetResponse;
import com.project.twitter.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tweet")
@AllArgsConstructor
public class TweetController {

    private final TweetService tweetService;
    private final TweetRepository tweetRepository;

    @PostMapping("/create/{username}")
    public ResponseEntity<?> createTweet(@RequestBody String text, @PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUsername = authentication.getName();

        if (authUsername.equals(username)) {
            TweetResponse tweetResponse = tweetService.createTweet(text, username);
            return new ResponseEntity<>(tweetResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Başkası için tweet atamazsın!", HttpStatus.FORBIDDEN);
        }
    }


    @PutMapping("/{tweetId}")
    public ResponseEntity<?> updateTweet(@PathVariable long tweetId, @RequestBody String text) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authUsername = authentication.getName();

            Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));

            if (authUsername.equals(tweet.getUser().getUsername())) {
                TweetResponse tweetResponse = tweetService.updateTweet(text, tweetId);
                return new ResponseEntity<>(tweetResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Başkasının tweetini güncelleyemezsin!", HttpStatus.FORBIDDEN);
            }
        } catch(TweetException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }

    }


    @DeleteMapping("/{tweetId}")
    public ResponseEntity<String> deleteTweet(@PathVariable long tweetId) {
       try {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String authUsername = authentication.getName();

           Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));

           if (authUsername.equals(tweet.getUser().getUsername())) {
               tweetService.deleteTweet(tweetId);
               return new ResponseEntity<>("Tweet silindi",HttpStatus.OK);
           } else {
               return new ResponseEntity<>("Başkasının tweetini silemezsin!", HttpStatus.FORBIDDEN);
           }
       } catch(TweetException e) {
           return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }

    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetResponse> findTweetByTweetId(@PathVariable long tweetId) {
        TweetResponse tweetResponse = tweetService.findTweetByTweetId(tweetId);
        return new ResponseEntity<>(tweetResponse, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Set<TweetResponse>> findAllTweets() {
        Set<TweetResponse> tweetResponses = tweetService.findAllTweets();
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<Set<TweetResponse>> findTweetsByUsername(@PathVariable String username) {
        Set<TweetResponse> tweetResponses = tweetService.findTweetsByUsername(username);
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }


    @GetMapping("/sort/date/asc")
    public ResponseEntity<List<TweetResponse>> sortTweetsByDateASC() {
        List<TweetResponse> tweetResponses = tweetService.sortTweetsByDateASC();
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }


    @GetMapping("/sort/date/desc")
    public ResponseEntity<List<TweetResponse>> sortTweetsByDateDESC() {
        List<TweetResponse> tweetResponses = tweetService.sortTweetsByDateDESC();
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }


    @GetMapping("/sort/comment/asc")
    public ResponseEntity<List<TweetResponse>> sortTweetsByCommentASC() {
        List<TweetResponse> tweetResponses = tweetService.sortTweetsByCommentASC();
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }


    @GetMapping("/sort/like/asc")
    public ResponseEntity<List<TweetResponse>> sortTweetsByLikeASC() {
        List<TweetResponse> tweetResponses = tweetService.sortTweetsByLikeASC();
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }


    @GetMapping("/sort/engagement/asc")
    public ResponseEntity<List<TweetResponse>> sortTweetsByEngagementASC() {
        List<TweetResponse> tweetResponses = tweetService.sortTweetsByEngagementASC();
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }

    @GetMapping("/sort/engagement/desc")
    public ResponseEntity<List<TweetResponse>> sortTweetsByEngagementDESC() {
        List<TweetResponse> tweetResponses = tweetService.sortTweetsByEngagementDESC();
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TweetResponse>> searchTweetByInput(@RequestParam String input) {
        List<TweetResponse> tweetResponses = tweetService.searchTweetByInput(input);
        return new ResponseEntity<>(tweetResponses, HttpStatus.OK);
    }
}
