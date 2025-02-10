package com.project.twitter.service.Impl;

import com.project.twitter.entity.*;
import com.project.twitter.exceptions.CommentException;
import com.project.twitter.exceptions.TweetException;
import com.project.twitter.exceptions.UserException;
import com.project.twitter.repository.*;
import com.project.twitter.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetLikeRepository tweetLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public void addTweetLike(long tweetId, String username) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        TweetLike tweetLike = new TweetLike();
        tweetLike.setUser(user);
        tweetLike.setTweet(tweet);
        tweetLikeRepository.save(tweetLike);
    }

    @Override
    public void removeTweetLike(long tweetId, String username) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        TweetLike tweetLike = tweetLikeRepository.findTweetLikeByTweetIdAndUsername(tweetId, username);
        tweetLikeRepository.delete(tweetLike);

    }

    @Override
    public void addCommentLike(long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CommentException("Yorum bulunamadı", HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));
        CommentLike commentLike = new CommentLike();
        commentLike.setUser(user);
        commentLike.setComment(comment);
        commentLikeRepository.save(commentLike);
    }

    @Override
    public void removeCommentLike(long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new CommentException("Yorum bulunamadı", HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));
        CommentLike commentLike = commentLikeRepository.findCommentLikeByTweetIdAndUsername(commentId, username);
        commentLikeRepository.delete(commentLike);
    }

    @Override
    public int getTweetLikeCount(long tweetId) {
        return tweetLikeRepository.findTweetLikeCount(tweetId);
    }

    @Override
    public int getCommentLikeCount(long commentId) {
        return commentLikeRepository.findCommentLikeCount(commentId);
    }
}
