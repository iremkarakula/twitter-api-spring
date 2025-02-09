package com.project.twitter.controller;


import com.project.twitter.requests.LoginRequest;
import com.project.twitter.requests.RegisterRequest;
import com.project.twitter.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){

        return authService.register(request);
    }


}
