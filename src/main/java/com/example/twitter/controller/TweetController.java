package com.example.twitter.controller;

import com.example.twitter.entity.Tweet;
import com.example.twitter.entity.User;
import com.example.twitter.service.TweetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@CrossOrigin
public class TweetController {

    private final TweetServiceImpl tweetService;

    @Autowired
    public TweetController(TweetServiceImpl tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        tweet.setUser(user);
        Tweet createdTweet = tweetService.createTweet(tweet);
        return ResponseEntity.ok(createdTweet);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Tweet>> getAllTweets() {
        List<Tweet> tweets = tweetService.getAllTweets();
        if (tweets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/{tweetId}")
    @Transactional(readOnly = true)
    public ResponseEntity<Tweet> getTweetById(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetService.getTweetById(tweetId));
    }

    @GetMapping("/user/{userId}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Tweet>> getTweetsByUserId(@PathVariable Long userId) {
        List<Tweet> tweets = tweetService.getTweetsByUserId(userId);
        if (tweets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tweets);
    }

    @DeleteMapping("/{tweetId}")
    @Transactional
    public ResponseEntity<Void> deleteTweet(@PathVariable Long tweetId, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        tweetService.deleteTweet(tweetId, user);
        return ResponseEntity.ok().build();
    }
}
