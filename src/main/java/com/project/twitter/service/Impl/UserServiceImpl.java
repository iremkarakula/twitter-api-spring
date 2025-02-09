package com.project.twitter.service.Impl;

import com.project.twitter.entity.User;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.requests.UpdateUserRequest;
import com.project.twitter.responses.GuestResponse;
import com.project.twitter.responses.UserResponse;
import com.project.twitter.service.UserService;
import com.project.twitter.util.PhoneAndEmailValidation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private PhoneAndEmailValidation phoneAndEmailValidation;

    @Override
    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
        return new UserResponse(user.getName(), user.getUsername(),user.getBirthDate()
                ,user.getRecordTime(),true, user.getEmail(), user.getPhoneNumber()
                , user.getBio(),user.getWebsite(), user.getLocation());

    }

    @Override
    public GuestResponse getGuestUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
        return new GuestResponse(user.getName(),user.getUsername(), user.getRecordTime(),false);
    }

    @Transactional
    @Override
    public User updateUser(String username, UpdateUserRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Kullanıcı bulunamadı"));
        user.setName(request.getName());
        user.setBirthDate(request.getBirthDate());

        phoneAndEmailValidation.updateEmailValidation(request, user);

        phoneAndEmailValidation.updatePhoneValidation(request,user);

        user.setBio(request.getBio());
        user.setWebsite(request.getWebsite());
        user.setLocation(request.getLocation());

        return userRepository.save(user);
    }
}
