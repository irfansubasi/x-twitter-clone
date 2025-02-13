package com.example.twitter.controller;

import com.example.twitter.entity.Comment;
import com.example.twitter.entity.User;
import com.example.twitter.exception.UnauthorizedException;
import com.example.twitter.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {

    private final CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {

        this.commentService = commentService;

    }

    @PostMapping
    @Transactional
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @AuthenticationPrincipal User user) {
        comment.setUser(user);
        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new UnauthorizedException("User is not authorized to comment.", HttpStatus.FORBIDDEN);
        }
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/tweet/{tweetId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Comment>> getCommentsByTweetId(@PathVariable Long tweetId) {
        List<Comment> comments = commentService.getCommentsByTweetId(tweetId);
        return ResponseEntity.ok(comments);
    }
}
