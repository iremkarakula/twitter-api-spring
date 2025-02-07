package com.project.twitter.controller;

import com.project.twitter.requests.LoginRequest;
import com.project.twitter.requests.RegisterRequest;
import com.project.twitter.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){
        authService.register(request);
        return "kayıt başarılı";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, HttpSession session){
        authService.login(request, session);
        return "Giriş başarılı";
    }
}
