package com.project.twitter.service;


import com.project.twitter.requests.LoginRequest;
import com.project.twitter.requests.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> register(RegisterRequest request);
    ResponseEntity<String> login(LoginRequest request);

}
