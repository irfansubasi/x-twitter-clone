package com.example.twitter.service;

import com.example.twitter.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Comment comment);

    List<Comment> getCommentsByTweetId(Long tweetId);

}
