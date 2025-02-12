package com.project.twitter.service.Impl;

import com.project.twitter.entity.Repost;
import com.project.twitter.entity.Tweet;
import com.project.twitter.entity.User;
import com.project.twitter.exceptions.RepostException;
import com.project.twitter.exceptions.TweetException;
import com.project.twitter.exceptions.UserException;
import com.project.twitter.repository.RepostRepository;
import com.project.twitter.repository.TweetRepository;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.responses.RepostResponse;
import com.project.twitter.service.RepostService;
import com.project.twitter.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RepostServiceImpl implements RepostService {

    private final RepostRepository repostRepository;
    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Override
    public RepostResponse createRepost(long tweetId, String username, String text) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(()-> new TweetException("Tweet bulunamadı", HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));
        Repost repost = new Repost(text, tweet, user);
        Repost saved = repostRepository.save(repost);
        return Mapper.toRepostResponse(saved);
    }

    @Override
    public void deleteRepost(long repostId) {
        repostRepository.findById(repostId)
                .orElseThrow(()-> new RepostException("Repost bulunamadı", HttpStatus.NOT_FOUND));
        repostRepository.deleteById(repostId);

    }

    @Override
    public List<RepostResponse> getAllRepost() {
       return repostRepository.findAll()
               .stream()
               .map(r -> Mapper.toRepostResponse(r))
               .sorted((x,y)->y.getRecordTime().compareTo(x.getRecordTime()))
               .toList();
    }

    @Override
    public List<RepostResponse> getAllRepostByTweetId(long tweetId) {
       return repostRepository.findRepostByTweetId(tweetId)
                .stream()
                .map(r -> Mapper.toRepostResponse(r))
                .sorted((x,y)->y.getRecordTime().compareTo(x.getRecordTime()))
                .toList();
    }

    @Override
    public List<RepostResponse> getAllRepostByUsername(String username) {
      return repostRepository.findRepostByUsername(username)
                .stream()
                .map(r -> Mapper.toRepostResponse(r))
                .sorted((x,y)->y.getRecordTime().compareTo(x.getRecordTime()))
                .toList();

    }
}
