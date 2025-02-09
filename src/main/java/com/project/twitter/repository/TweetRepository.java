package com.project.twitter.repository;

import com.project.twitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("SELECT t FROM Tweet t WHERE t.user.username = :username")
    List<Tweet> findAllByUsername(String username);

    @Query("SELECT t FROM Tweet t ORDER BY t.recordTime ASC")
    List<Tweet> sortTweetsByDateASC();

    @Query("SELECT t FROM Tweet t ORDER BY t.recordTime DESC")
    List<Tweet> sortTweetsByDateDESC();

    @Query("SELECT t FROM Tweet t ORDER BY SIZE(t.comments) ASC")
    List<Tweet> sortTweetsByCommentASC();

    @Query("SELECT t FROM Tweet t ORDER BY SIZE(t.likes) ASC")
    List<Tweet> sortTweetsByLikeASC();

    @Query("SELECT t FROM Tweet t ORDER BY SIZE(t.likes) + SIZE(t.comments) + SIZE(t.reposts) ASC")
    List<Tweet> sortTweetsByEngagementASC();

    @Query("SELECT t FROM Tweet t ORDER BY SIZE(t.likes) + SIZE(t.comments) + SIZE(t.reposts) DESC")
    List<Tweet> sortTweetsByEngagementDESC();

    @Query("SELECT t FROM Tweet t WHERE t.text LIKE %:input%")
    List<Tweet> searchTweetByInput(String input);
}
