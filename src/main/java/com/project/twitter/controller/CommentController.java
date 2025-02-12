package com.project.twitter.controller;


import com.project.twitter.entity.Comment;
import com.project.twitter.exceptions.CommentException;
import com.project.twitter.exceptions.UserException;
import com.project.twitter.repository.CommentRepository;
import com.project.twitter.requests.CommentRequest;
import com.project.twitter.responses.CommentResponse;
import com.project.twitter.service.CommentService;
import com.project.twitter.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;


    @PostMapping("/create/{tweetId}")
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody CommentRequest request,
            @PathVariable long tweetId) {

        String authUsername = AuthUtil.getAuthenticatedUsername();

        if (authUsername != null) {
            return new ResponseEntity<>(commentService.createComment(request.getText(), authUsername, tweetId), HttpStatus.CREATED);
        } else {
            throw  new UserException("Başkası için yorum yapamazsın!", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(
            @RequestBody CommentRequest request,
            @PathVariable long commentId) {
        try {
            String authUsername = AuthUtil.getAuthenticatedUsername();

            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(()->new CommentException("Yorum bulunamadı", HttpStatus.NOT_FOUND));

            if (authUsername.equals(comment.getUser().getUsername())) {
                CommentResponse commentResponse = commentService.updateComment(request.getText(), commentId);
                return new ResponseEntity<>(commentResponse, HttpStatus.OK);
            } else {
                throw new CommentException("Başkasının yorumunu güncelleyemezsin!", HttpStatus.FORBIDDEN);
            }
        } catch(CommentException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId) {
        try {
            String authUsername = AuthUtil.getAuthenticatedUsername();

            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(()->new CommentException("Yorum bulunamadı", HttpStatus.NOT_FOUND));

            if (authUsername.equals(comment.getUser().getUsername())) {
                commentService.deleteComment(commentId);
                return new ResponseEntity<>("Yorum silindi",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Başkasının yorumunu silemezsin!", HttpStatus.FORBIDDEN);
            }
        } catch(CommentException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable long commentId) {
        return new ResponseEntity<>(commentService.findCommentByCommentId(commentId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        return new ResponseEntity<>(commentService.findAllComments(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<CommentResponse>> getCommentsByUsername() {
        String authUsername = AuthUtil.getAuthenticatedUsername();
        return new ResponseEntity<>(commentService.findCommentsByUsername(authUsername), HttpStatus.OK);
    }

    @GetMapping("/tweet/{tweetId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByTweetId(@PathVariable long tweetId) {
        return new ResponseEntity<>(commentService.findCommentsByTweetId(tweetId), HttpStatus.OK);
    }

    @GetMapping("/sort/date/asc")
    public ResponseEntity<List<CommentResponse>> sortCommentsByDateASC() {
        return ResponseEntity.ok(commentService.sortCommentsByDateASC());
    }

    @GetMapping("/sort/date/desc")
    public ResponseEntity<List<CommentResponse>> sortCommentsByDateDESC() {
        return ResponseEntity.ok(commentService.sortCommentsByDateDESC());
    }

    @GetMapping("/sort/likes/asc")
    public ResponseEntity<List<CommentResponse>> sortCommentsByLikeASC() {
        return ResponseEntity.ok(commentService.sortCommentsByLikeASC());
    }

    @GetMapping("/sort/engagement/asc")
    public ResponseEntity<List<CommentResponse>> sortCommentsByEngagementASC() {
        return ResponseEntity.ok(commentService.sortCommentsByEngagementASC());
    }
}
