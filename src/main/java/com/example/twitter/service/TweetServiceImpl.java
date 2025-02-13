package com.example.twitter.service;

import com.example.twitter.entity.Tweet;
import com.example.twitter.entity.User;
import com.example.twitter.exception.TweetException;
import com.example.twitter.repository.TweetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    @Transactional
    public Tweet createTweet(Tweet tweet) {
        log.debug("Tweet oluşturuluyor: {}", tweet);
        return tweetRepository.save(tweet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tweet> getAllTweets() {
        log.debug("Tüm tweetler çekiliyor");
        return tweetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Tweet getTweetById(Long tweetId) {
        log.debug("Bu id ile tweet çekiliyor: {}", tweetId);
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetException("Bu id'de tweet bulunamadı: " + tweetId, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tweet> getTweetsByUserId(Long userId) {
        log.debug("Bu id'li kullanıcı tweetleri çekiliyor: {}", userId);
        List<Tweet> tweets = tweetRepository.findByUserUserId(userId);
        if (tweets.isEmpty()) {
            throw new TweetException("Bu kullanıcıya ait tweet bulunamadı", HttpStatus.NO_CONTENT);
        }
        return tweets;
    }

    @Override
    @Transactional
    public void deleteTweet(Long tweetId, User user) {
        log.debug("Bu id'li tweet siliniyor: {} bu kullanıcı tarafından: {}", tweetId, user.getUsername());
        Tweet tweet = getTweetById(tweetId);

        if (!tweet.getUser().getUserId().equals(user.getUserId()) && !user.getRole().name().equals("ROLE_ADMIN")) {
            throw new TweetException("Kullanıcının bu tweeti silmeye yetkisi yok", HttpStatus.FORBIDDEN);
        }
        
        tweetRepository.delete(tweet);
        log.debug("Tweet başarıyla silindi");
    }
}
