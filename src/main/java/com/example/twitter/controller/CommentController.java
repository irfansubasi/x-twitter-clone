package com.example.twitter.controller;

import com.example.twitter.entity.Comment;
import com.example.twitter.service.CommentService;
import com.example.twitter.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @GetMapping("/tweet/{tweetId}")
    public List<Comment> getCommentsByTweetId(@PathVariable Long tweetId) {
        return commentService.getCommentsByTweetId(tweetId);
    }
}
