package com.example.twitter.controller;


import com.example.twitter.entity.Retweet;
import com.example.twitter.service.RetweetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweets")
public class RetweetController {

    private RetweetServiceImpl retweetService;

    @Autowired
    public RetweetController(RetweetServiceImpl retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping
    public Retweet retweet(@RequestBody Retweet retweet) {
        return retweetService.retweet(retweet);
    }

    @DeleteMapping("/tweet/{tweetId}/user/{userId}")
    public void deleteRetweet(@PathVariable Long tweetId, @PathVariable Long userId) {
        retweetService.deleteRetweet(tweetId, userId);
    }
}
