package com.example.twitter.service;

import com.example.twitter.entity.Retweet;
import com.example.twitter.repository.RetweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RetweetServiceImpl implements RetweetService{

    private RetweetRepository retweetRepository;

    @Autowired
    public RetweetServiceImpl(RetweetRepository retweetRepository) {
        this.retweetRepository = retweetRepository;
    }

    @Override
    public Retweet retweet(Retweet retweet) {
        return retweetRepository.save(retweet);
    }

    @Override
    public void deleteRetweet(Long tweetId, Long userId) {
        Optional<Retweet> retweet = retweetRepository.findByTweetTweetIdAndUserUserId(tweetId, userId);
        retweet.ifPresent(retweetRepository::delete);
    }
}
