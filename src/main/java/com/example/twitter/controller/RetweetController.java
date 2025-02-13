package com.example.twitter.controller;

import com.example.twitter.entity.Retweet;
import com.example.twitter.entity.Tweet;
import com.example.twitter.entity.User;
import com.example.twitter.exception.TweetException;
import com.example.twitter.service.RetweetServiceImpl;
import com.example.twitter.service.TweetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweets")
@CrossOrigin
public class RetweetController {

    private final RetweetServiceImpl retweetService;
    private final TweetServiceImpl tweetService;

    @Autowired
    public RetweetController(RetweetServiceImpl retweetService, TweetServiceImpl tweetService) {
        this.retweetService = retweetService;
        this.tweetService = tweetService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Retweet> retweet(@RequestBody Tweet tweet, @AuthenticationPrincipal User user) {
        Tweet originalTweet = tweetService.getTweetById(tweet.getTweetId());
        
        Retweet retweet = new Retweet();
        retweet.setTweet(originalTweet);
        retweet.setUser(user);
        
        Retweet createdRetweet = retweetService.retweet(retweet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRetweet);
    }

    @DeleteMapping("/tweet/{tweetId}/user/{userId}")
    @Transactional
    public ResponseEntity<Void> deleteRetweet(@PathVariable Long tweetId, @PathVariable Long userId, @AuthenticationPrincipal User user) {
        if (!userId.equals(user.getUserId())) {
            throw new TweetException("Retweet bulunamadı veya yetkisiz işlem", HttpStatus.NOT_FOUND);
        }
        retweetService.deleteRetweet(tweetId, userId);
        return ResponseEntity.ok().build();
    }
}
