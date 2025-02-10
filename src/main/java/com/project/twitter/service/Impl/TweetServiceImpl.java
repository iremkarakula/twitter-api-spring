package com.project.twitter.service.Impl;

import com.project.twitter.entity.*;
import com.project.twitter.exceptions.TweetException;
import com.project.twitter.exceptions.UserException;
import com.project.twitter.repository.TweetRepository;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.responses.TweetResponse;
import com.project.twitter.service.TweetService;
import com.project.twitter.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TweetServiceImpl implements TweetService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;


    @Transactional
    @Override
    public TweetResponse createTweet(String text, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        Tweet tweet = new Tweet();
        tweet.setText(text);
        tweet.setUser(user);

        Tweet savedTweet = tweetRepository.save(tweet);

        return Mapper.toTweetResponse(savedTweet);
    }

    @Transactional
    @Override
    public TweetResponse updateTweet(String text, long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));
        tweet.setText(text);
        tweetRepository.save(tweet);
        return Mapper.toTweetResponse(tweet);
    }

    @Override
    public void deleteTweet(long tweetId) {
        tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));
        tweetRepository.deleteById(tweetId);
    }

    @Override
    public TweetResponse findTweetByTweetId(long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(()-> new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));
        return Mapper.toTweetResponse(tweet);
    }

    @Override
    public Set<TweetResponse> findAllTweets() {

        return tweetRepository.findAll()
                .stream()
                .map(t -> Mapper.toTweetResponse(t))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TweetResponse> findTweetsByUsername(String username) {
        return tweetRepository.findAllByUsername(username)
                .stream()
                .map(t-> Mapper.toTweetResponse(t))
                .collect(Collectors.toSet());

    }

    @Override
    public List<TweetResponse> sortTweetsByDateASC() {
        return tweetRepository.sortTweetsByDateASC()
                .stream()
                .map(t-> Mapper.toTweetResponse(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> sortTweetsByDateDESC() {
        return tweetRepository.sortTweetsByDateDESC()
                .stream()
                .map(t-> Mapper.toTweetResponse(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> sortTweetsByCommentASC() {
        return tweetRepository.sortTweetsByCommentASC()
                .stream()
                .map(t-> Mapper.toTweetResponse(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> sortTweetsByLikeASC() {
        return tweetRepository.sortTweetsByLikeASC()
                .stream()
                .map(t-> Mapper.toTweetResponse(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> sortTweetsByEngagementASC() {
        return tweetRepository.sortTweetsByEngagementASC()
                .stream()
                .map(t-> Mapper.toTweetResponse(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> sortTweetsByEngagementDESC() {
        return tweetRepository.sortTweetsByEngagementDESC()
                .stream()
                .map(t-> Mapper.toTweetResponse(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> searchTweetByInput(String input) {
        return tweetRepository.searchTweetByInput(input)
                .stream()
                .map(t-> Mapper.toTweetResponse(t))
                .collect(Collectors.toList());
    }
}
