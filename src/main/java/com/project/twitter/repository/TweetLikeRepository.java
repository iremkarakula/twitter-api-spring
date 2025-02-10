package com.project.twitter.repository;

import com.project.twitter.entity.TweetLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TweetLikeRepository extends JpaRepository<TweetLike, Long> {

    @Query("SELECT l FROM TweetLike l WHERE l.tweet.id = :tweetId AND l.user.username = :username")
    TweetLike findTweetLikeByTweetIdAndUsername(long tweetId, String username);

    @Query("SELECT COUNT(l) FROM TweetLike l WHERE l.tweet.id = :tweetId")
    int findTweetLikeCount(long tweetId);
}
