package com.example.twitter.controller;


import com.example.twitter.entity.Like;
import com.example.twitter.service.LikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private LikeServiceImpl likeService;

    @Autowired
    public LikeController(LikeServiceImpl likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public Like likeTweet(@RequestBody Like like) {
        return likeService.likeTweet(like);
    }

    @DeleteMapping("/tweet/{tweetId}/user/{userId}")
    public void unlikeTweet(@PathVariable Long tweetId, @PathVariable Long userId) {
        likeService.unlikeTweet(tweetId, userId);
    }
}
