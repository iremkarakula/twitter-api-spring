package com.project.twitter.service.Impl;

import com.project.twitter.entity.Tweet;
import com.project.twitter.entity.User;
import com.project.twitter.exceptions.TweetException;
import com.project.twitter.exceptions.UserException;
import com.project.twitter.repository.TweetRepository;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.responses.TweetResponse;
import com.project.twitter.service.TweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    private TweetService tweetService;

    private User user;
    private Tweet tweet1;
    private Tweet tweet2;

    @BeforeEach
    void setUp() {
        tweetService = new TweetServiceImpl(userRepository, tweetRepository);

        user = new User();
        user.setUsername("testUser");
        user.setName("Test User");
        user.setBirthDate(new Date(1990, 5, 15));
        user.setEmail("testuser@example.com");
        user.setPassword("password123");

        tweet1 = new Tweet();
        tweet1.setText("This is the first tweet!");
        tweet1.setUser(user);

        tweet2 = new Tweet();
        tweet2.setText("This is the second tweet!");
        tweet2.setUser(user);

        userRepository.save(user);


    }

    @Test
    void createTweet() {

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(tweetRepository.save(tweet1)).thenReturn(tweet1);

        TweetResponse response = tweetService.createTweet("This is the first tweet!", "testUser");

        assertNotNull(response);
        assertEquals("This is the first tweet!", response.getText());
        assertEquals("testUser", response.getUsername());


        verify(userRepository).findByUsername("testUser");
        verify(tweetRepository).save(tweet1);
    }

    @Test
    void createTweet_withNoUser() {
         Tweet tweet3 = new Tweet();
        tweet3.setText("creation test tweet with no user");
        when(userRepository.findByUsername("noUser")).thenReturn(Optional.empty());

        assertThatThrownBy(()->tweetService.createTweet("creation test tweet with no user", "noUser"))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("Kullanıcı bulunamadı");

        verify(tweetRepository, never()).save(tweet3);
    }

    @Test
    void updateTweet() {
        when(tweetRepository.findById(tweet1.getId())).thenReturn(Optional.of(tweet1));
        when(tweetRepository.save(tweet1)).thenReturn(tweet1);

        TweetResponse response = tweetService.updateTweet("update tweet", tweet1.getId());

        assertNotNull(response);
        assertEquals("update tweet",response.getText());
        assertEquals("testUser",response.getUsername());

        verify(tweetRepository, times(1)).findById(tweet1.getId());
        verify(tweetRepository, times(1)).save(tweet1);
    }

    @Test
    void updateTweet_withNoTweet() {
        when(tweetRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->tweetService.updateTweet("update text with no tweet", 5L))
                .isInstanceOf(TweetException.class)
                .hasMessageContaining("Tweet bulunamadı");


        verify(tweetRepository, never()).save(tweet1);

    }

    @Test
    void deleteTweet() {
        when(tweetRepository.findById(tweet1.getId())).thenReturn(Optional.of(tweet1));
        doNothing().when(tweetRepository).deleteById(tweet1.getId());

        tweetService.deleteTweet(tweet1.getId());

        verify(tweetRepository, times(1)).findById(tweet1.getId());
        verify(tweetRepository, times(1)).deleteById(tweet1.getId());
    }

    @Test
    void deleteTweet_withNoTweet() {
        when(tweetRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->tweetService.deleteTweet(5L))
                .isInstanceOf(TweetException.class)
                .hasMessageContaining("Tweet bulunamadı");

        verify(tweetRepository, times(1)).findById(5L);
        verify(tweetRepository, never()).deleteById(tweet1.getId());
    }

    @Test
    void findTweetByTweetId() {
        when(tweetRepository.findById(tweet1.getId())).thenReturn(Optional.of(tweet1));
        TweetResponse response = tweetService.findTweetByTweetId(tweet1.getId());
        assertNotNull(response);
        assertEquals("testUser", response.getUsername());
        assertEquals("This is the first tweet!", response.getText());
        verify(tweetRepository, times(1)).findById(tweet1.getId());
    }


    @Test
    void findTweetByTweetId_withNoTweet() {
        when(tweetRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->tweetService.findTweetByTweetId(5L))
                .isInstanceOf(TweetException.class)
                .hasMessageContaining("Tweet bulunamadı");
        verify(tweetRepository, times(1)).findById(5L);
    }

    @Test
    void findAllTweets() {
        when(tweetRepository.findAll()).thenReturn(List.of(tweet1, tweet2));
        Set<TweetResponse>  responses = tweetService.findAllTweets();
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(r -> r.getText().equals("This is the first tweet!")));
        assertTrue(responses.stream().anyMatch(r -> r.getText().equals("This is the second tweet!")));
        assertTrue(responses.stream().allMatch(r -> r.getUsername().equals("testUser")));

        verify(tweetRepository, times(1)).findAll();
    }

    @Test
    void findAllTweets_withNoTweet() {
        when(tweetRepository.findAll()).thenReturn(List.of());
        Set<TweetResponse> tweetResponses = tweetService.findAllTweets();
        assertNotNull(tweetResponses);
        assertTrue(tweetResponses.isEmpty());
        verify(tweetRepository, times(1)).findAll();
    }

    @Test
    void findTweetsByUsername() {
        when(tweetRepository.findAllByUsername("testUser")).thenReturn(List.of(tweet1, tweet2));
        Set<TweetResponse> tweetResponses = tweetService.findTweetsByUsername("testUser");

        assertEquals(2, tweetResponses.size());
        tweetResponses.stream().allMatch(t->t.getUsername().equals("testUser"));

        tweetResponses.stream().anyMatch(t->t.getText().equals("This is the first tweet!"));
        tweetResponses.stream().anyMatch(t->t.getText().equals("This is the second tweet!"));

        verify(tweetRepository, times(1)).findAllByUsername("testUser");
    }

}