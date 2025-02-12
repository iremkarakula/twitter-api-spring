package com.project.twitter.repository;

import com.project.twitter.entity.Repost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepostRepository extends JpaRepository<Repost, Long> {

    @Query("SELECT r FROM Repost r WHERE r.tweet.id = :tweetId")
    List<Repost> findRepostByTweetId(long tweetId);

    @Query("SELECT r FROM Repost r WHERE r.user.username = :username")
    List<Repost> findRepostByUsername(String username);
}
