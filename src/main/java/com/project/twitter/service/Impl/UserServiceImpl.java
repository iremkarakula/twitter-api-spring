package com.project.twitter.service.Impl;

import com.project.twitter.entity.User;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.requests.UpdateUserRequest;
import com.project.twitter.responses.GuestResponse;
import com.project.twitter.responses.UserResponse;
import com.project.twitter.service.UserService;
import com.project.twitter.util.PhoneAndEmailValidation;
import com.project.twitter.util.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PhoneAndEmailValidation phoneAndEmailValidation;
    private UserMapper userMapper;

    @Override
    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
        return userMapper.toUserResponse(user);

    }

    @Override
    public GuestResponse getGuestUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
        return userMapper.toGuestResponse(user);
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

    @Override
    public Set<GuestResponse> searchUsersByInput(String input) {
        return userRepository.searchUsersByInput(input)
                .stream().map(u -> userMapper.toGuestResponse(u))
                .collect(Collectors.toSet());
    }
}
