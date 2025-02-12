package com.project.twitter.repository;

import com.project.twitter.entity.Tweet;
import com.project.twitter.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TweetRepositoryTest {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;



    @BeforeEach
    public void setUp() {

        User user = new User();
        user.setUsername("testUser");
        user.setName("Test User");
        user.setBirthDate(new Date(1990, 5, 15));
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        userRepository.save(user);

        Tweet tweet1 = new Tweet();
        tweet1.setText("First tweet");
        tweet1.setUser(user);
        tweetRepository.save(tweet1);

        Tweet tweet2 = new Tweet();
        tweet2.setText("Second Tweet");
        tweet2.setUser(user);
        tweetRepository.save(tweet2);

    }


    @Test
    void findAllByUsername() {
       List<Tweet> tweets = tweetRepository.findAllByUsername("testUser");

       assertNotNull(tweets);
       assertEquals(2, tweets.size());

       assertTrue(tweets.stream().allMatch(t -> t.getUser().getUsername().equals("testUser")));
       assertEquals("First tweet", tweets.get(0).getText());
       assertEquals("Second Tweet", tweets.get(1).getText());
       tweets.forEach(t -> assertEquals("testUser", t.getUser().getUsername()));
    }

    @Test
    void findAllByUsername_withNoTweets() {
        List<Tweet> tweets = tweetRepository.findAllByUsername("noUser");

        assertNotNull(tweets);
        assertEquals(0, tweets.size());
    }

    @Test
    void sortTweetsByDateASC() {
        List<Tweet> tweets = tweetRepository.sortTweetsByDateASC();
        assertTrue(tweets.get(0).getRecordTime().before(tweets.get(1).getRecordTime()));
    }

    @Test
    void sortTweetsByDateDESC() {
        List<Tweet> tweets = tweetRepository.sortTweetsByDateDESC();
        assertTrue(tweets.get(0).getRecordTime().after(tweets.get(1).getRecordTime()));
    }

    @Test
    void searchTweetByInput() {
        List<Tweet> tweets = tweetRepository.searchTweetByInput("tw");
        assertNotNull(tweets);
        assertEquals(2, tweets.size());
        assertTrue(tweets.stream().anyMatch(t -> t.getText().equals("First tweet")));
        assertTrue(tweets.stream().anyMatch(t -> t.getText().equals("Second Tweet")));
    }
}
