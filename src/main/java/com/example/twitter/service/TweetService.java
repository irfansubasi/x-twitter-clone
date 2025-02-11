package com.example.twitter.service;

import com.example.twitter.entity.Tweet;

import java.util.List;

public interface TweetService {

    Tweet createTweet(Tweet tweet);

    List<Tweet> getTweetsByUserId(Long userId);

}
