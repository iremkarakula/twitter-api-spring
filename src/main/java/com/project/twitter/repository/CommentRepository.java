package com.project.twitter.repository;

import com.project.twitter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.user.username = :username")
    List<Comment> findAllByUsername(String username);

    @Query("SELECT c FROM Comment c WHERE c.tweet.id = :id")
    List<Comment> findAllByTweetId(long id);

    @Query("SELECT c FROM Comment c ORDER BY c.recordTime ASC")
    List<Comment> sortCommentsByDateASC();

    @Query("SELECT c FROM Comment c ORDER BY c.recordTime DESC")
    List<Comment> sortCommentsByDateDESC();

    @Query("SELECT c FROM Comment c ORDER BY SIZE(c.likes) ASC")
    List<Comment> sortCommentsByLikeASC();

    @Query("SELECT c FROM Comment c ORDER BY SIZE(c.likes)+SIZE(c.reposts) ASC")
    List<Comment> sortCommentsByEngagementASC();
}
