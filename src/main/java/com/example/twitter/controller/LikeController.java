package com.example.twitter.controller;

import com.example.twitter.entity.Like;
import com.example.twitter.entity.Tweet;
import com.example.twitter.entity.User;
import com.example.twitter.exception.TweetException;
import com.example.twitter.exception.UnauthorizedException;
import com.example.twitter.service.LikeServiceImpl;
import com.example.twitter.service.TweetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@CrossOrigin
public class LikeController {

    private final LikeServiceImpl likeService;
    private final TweetServiceImpl tweetService;

    @Autowired
    public LikeController(LikeServiceImpl likeService, TweetServiceImpl tweetService) {
        this.likeService = likeService;
        this.tweetService = tweetService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Like> likeTweet(@RequestBody Like like, @AuthenticationPrincipal User user) {
        Tweet tweet = tweetService.getTweetById(like.getTweet().getTweetId());
        if (tweet == null) {
            throw new TweetException("tweet not found", HttpStatus.NOT_FOUND);
        }
        like.setTweet(tweet);
        like.setUser(user);
        Like createdLike = likeService.likeTweet(like);
        return ResponseEntity.ok(createdLike);
    }

    @DeleteMapping("/tweet/{tweetId}/user/{userId}")
    @Transactional
    public ResponseEntity<Void> unlikeTweet(@PathVariable Long tweetId, @PathVariable Long userId, @AuthenticationPrincipal User user) {
        if (!userId.equals(user.getUserId())) {
            throw new UnauthorizedException("User not authorized to unlike this tweet", HttpStatus.FORBIDDEN);
        }
        likeService.unlikeTweet(tweetId, userId);
        return ResponseEntity.ok().build();
    }
}
