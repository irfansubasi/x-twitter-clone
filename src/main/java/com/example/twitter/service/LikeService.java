package com.example.twitter.service;

import com.example.twitter.entity.Like;

public interface LikeService {

    Like likeTweet(Like like);

    void unlikeTweet(Long tweetId, Long userId);

}
