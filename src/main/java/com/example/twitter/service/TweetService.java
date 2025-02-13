package com.example.twitter.service;

import com.example.twitter.entity.Tweet;
import com.example.twitter.entity.User;

import java.util.List;

public interface TweetService {

    Tweet createTweet(Tweet tweet);

    List<Tweet> getAllTweets();

    Tweet getTweetById(Long tweetId);

    List<Tweet> getTweetsByUserId(Long userId);

    void deleteTweet(Long tweetId, User user);

}
