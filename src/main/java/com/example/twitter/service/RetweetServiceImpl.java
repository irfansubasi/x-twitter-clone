package com.example.twitter.service;

import com.example.twitter.entity.Retweet;
import com.example.twitter.repository.RetweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class RetweetServiceImpl implements RetweetService {

    private final RetweetRepository retweetRepository;

    @Autowired
    public RetweetServiceImpl(RetweetRepository retweetRepository) {
        this.retweetRepository = retweetRepository;
    }

    @Override
    @Transactional
    public Retweet retweet(Retweet retweet) {
        Optional<Retweet> existingRetweet = retweetRepository.findByTweetTweetIdAndUserUserId(
                retweet.getTweet().getTweetId(),
                retweet.getUser().getUserId()
        );

        if (existingRetweet.isPresent()) {
            log.debug("Retweet zaten var");
            return existingRetweet.get();
        }

        return retweetRepository.save(retweet);
    }

    @Override
    @Transactional
    public void deleteRetweet(Long tweetId, Long userId) {
        log.debug("Şu tweet için retweet siliniyor: {} bu kullanıcı tarafından: {}", tweetId, userId);
        Optional<Retweet> retweet = retweetRepository.findByTweetTweetIdAndUserUserId(tweetId, userId);
        retweet.ifPresent(value -> {
            log.debug("retweet bulundu. Siliniyor");
            retweetRepository.delete(value);
        });
    }
}
