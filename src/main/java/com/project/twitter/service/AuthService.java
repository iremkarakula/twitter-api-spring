package com.project.twitter.service;

import com.project.twitter.entity.User;
import com.project.twitter.requests.LoginRequest;
import com.project.twitter.requests.RegisterRequest;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    void register(RegisterRequest request);
    void login(LoginRequest request, HttpSession session);


    Boolean isAuthenticated(String username, String password);
}
