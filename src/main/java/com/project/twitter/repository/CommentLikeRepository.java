package com.project.twitter.repository;

import com.project.twitter.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("SELECT l FROM CommentLike l WHERE l.comment.id = :commentId AND l.user.username = :username")
    CommentLike findCommentLikeByTweetIdAndUsername(long commentId, String username);

    @Query("SELECT COUNT(l) FROM CommentLike l WHERE l.comment.id = :commentId")
    int findCommentLikeCount(long commentId);
}
