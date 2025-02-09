package com.project.twitter.controller;


import com.project.twitter.requests.UpdateUserRequest;
import com.project.twitter.responses.GuestResponse;
import com.project.twitter.responses.UserResponse;
import com.project.twitter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInUsername = authentication.getName();

            if (loggedInUsername.equals(username)) {
                UserResponse userResponse = userService.getUser(username);
                return new ResponseEntity<>(userResponse, HttpStatus.OK);
            } else {
                GuestResponse guestResponse = userService.getGuestUser(username);
                return new ResponseEntity<>(guestResponse, HttpStatus.OK);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND);
        }
    }




    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UpdateUserRequest request){
       try{
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String authUsername = authentication.getName();

           if (authUsername.equals(username)) {
               userService.updateUser(username, request);
               return new ResponseEntity<>("Profil güncelleme başarılı", HttpStatus.OK);
           } else {
               return new ResponseEntity<>("Başkasının profilini güncelleyemezsiniz!", HttpStatus.FORBIDDEN);
           }
       }catch (ResponseStatusException e) {
           return new ResponseEntity<>(e.getReason(), e.getStatusCode());
       }
    }

    @GetMapping("/search")
    public ResponseEntity<Set<?>> searchUsersByInput(@RequestParam String input) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUsername = authentication.getName();

        Set<GuestResponse> guestResponses = userService.searchUsersByInput(input);

        guestResponses.removeIf(guestResponse -> guestResponse.getUsername().equals(authUsername));

        return new ResponseEntity<>(guestResponses, HttpStatus.OK);
    }
}
