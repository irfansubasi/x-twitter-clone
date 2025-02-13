package com.example.twitter.service;

import com.example.twitter.entity.Like;
import com.example.twitter.repository.LikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    @Transactional
    public Like likeTweet(Like like) {
        log.debug("Bu tweet için like kaydediliyor: {} bu kullanıcı tarafından: {}", like.getTweet().getTweetId(), like.getUser().getUsername());
        Optional<Like> existingLike = likeRepository.findByTweetTweetIdAndUserUserId(
                like.getTweet().getTweetId(), 
                like.getUser().getUserId()
        );
        
        if (existingLike.isPresent()) {
            log.debug("Zaten like var.");
            return existingLike.get();
        }
        
        return likeRepository.save(like);
    }

    @Override
    @Transactional
    public void unlikeTweet(Long tweetId, Long userId) {
        log.debug("RBu tweet için like siliniyor: {} bu kullanıcı tarafından: {}", tweetId, userId);
        Optional<Like> like = likeRepository.findByTweetTweetIdAndUserUserId(tweetId, userId);
        like.ifPresent(value -> {
            log.debug("Like bulundu. Siliniyor.");
            likeRepository.delete(value);
        });
    }
}
