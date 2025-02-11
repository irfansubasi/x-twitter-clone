package com.example.twitter.controller;

import com.example.twitter.entity.Tweet;
import com.example.twitter.service.TweetService;
import com.example.twitter.service.TweetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private TweetServiceImpl tweetService;

    @Autowired
    public TweetController(TweetServiceImpl tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public Tweet createTweet(@RequestBody Tweet tweet) {
        return tweetService.createTweet(tweet);
    }

    @GetMapping("/user/{userId}")
    public List<Tweet> getTweetsByUserId(@PathVariable Long userId) {
        return tweetService.getTweetsByUserId(userId);
    }
}
