package com.example.twitter.service;

import com.example.twitter.entity.Retweet;

public interface RetweetService {

    Retweet retweet(Retweet retweet);

    void deleteRetweet(Long tweetId, Long userId);

}
