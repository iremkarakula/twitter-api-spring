package com.project.twitter.service;

import com.project.twitter.responses.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(String text, String username, long tweetId);
    CommentResponse updateComment(String text, long commentId);
    void deleteComment(long commentId);

    CommentResponse findCommentByCommentId(long commentId);
    List<CommentResponse> findAllComments();
    List<CommentResponse> findCommentsByUsername(String username);
    List<CommentResponse> findCommentsByTweetId(long tweetId);

    List<CommentResponse> sortCommentsByDateASC();
    List<CommentResponse> sortCommentsByDateDESC();
    List<CommentResponse> sortCommentsByLikeASC();
    List<CommentResponse> sortCommentsByEngagementASC();

}
