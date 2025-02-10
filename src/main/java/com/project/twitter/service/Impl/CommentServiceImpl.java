package com.project.twitter.service.Impl;

import com.project.twitter.entity.Comment;
import com.project.twitter.entity.Tweet;
import com.project.twitter.entity.User;
import com.project.twitter.exceptions.CommentException;
import com.project.twitter.exceptions.TweetException;
import com.project.twitter.exceptions.UserException;
import com.project.twitter.repository.CommentRepository;
import com.project.twitter.repository.TweetRepository;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.responses.CommentResponse;
import com.project.twitter.service.CommentService;
import com.project.twitter.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;


    @Override
    public CommentResponse createComment(String text, String username,long tweetId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(()->new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setTweet(tweet);
        Comment saved = commentRepository.save(comment);
        return Mapper.toCommentResponse(saved);
    }

    @Override
    public CommentResponse updateComment(String text, long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentException("Yorum bulunamadı", HttpStatus.NOT_FOUND));
        comment.setText(text);
        commentRepository.save(comment);
        Comment saved = commentRepository.save(comment);
        return Mapper.toCommentResponse(saved);
    }

    @Override
    public void deleteComment(long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentException("Yorum bulunamadı", HttpStatus.NOT_FOUND));
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponse findCommentByCommentId(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentException("Yorum bulunamadı", HttpStatus.NOT_FOUND));
        return Mapper.toCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> findAllComments() {
        return commentRepository
                .findAll()
                .stream()
                .map(c -> Mapper.toCommentResponse(c)).toList();
    }

    @Override
    public List<CommentResponse> findCommentsByUsername(String username) {
        return commentRepository
                .findAllByUsername(username)
                .stream()
                .map(c -> Mapper.toCommentResponse(c)).toList();
    }

    @Override
    public List<CommentResponse> findCommentsByTweetId(long tweetId) {
        return commentRepository
                .findAllByTweetId(tweetId)
                .stream()
                .map(c -> Mapper.toCommentResponse(c)).toList();
    }

    @Override
    public List<CommentResponse> sortCommentsByDateASC() {
        return commentRepository
                .sortCommentsByDateASC()
                .stream()
                .map(c -> Mapper.toCommentResponse(c)).toList();
    }

    @Override
    public List<CommentResponse> sortCommentsByDateDESC() {
        return commentRepository
                .sortCommentsByDateDESC()
                .stream()
                .map(c -> Mapper.toCommentResponse(c)).toList();
    }

    @Override
    public List<CommentResponse> sortCommentsByLikeASC() {
        return commentRepository
                .sortCommentsByLikeASC()
                .stream()
                .map(c -> Mapper.toCommentResponse(c)).toList();
    }

    @Override
    public List<CommentResponse> sortCommentsByEngagementASC() {
        return commentRepository
                .sortCommentsByEngagementASC()
                .stream()
                .map(c -> Mapper.toCommentResponse(c)).toList();
    }
}
