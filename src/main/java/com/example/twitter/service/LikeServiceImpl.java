package com.example.twitter.service;


import com.example.twitter.entity.Like;
import com.example.twitter.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService{

    private LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public Like likeTweet(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void unlikeTweet(Long tweetId, Long userId) {
        Optional<Like> like = likeRepository.findByTweetTweetIdAndUserUserId(tweetId, userId);
        like.ifPresent(likeRepository::delete);
    }
}
