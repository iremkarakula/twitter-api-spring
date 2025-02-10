package com.project.twitter.controller;


import com.project.twitter.requests.UpdateUserRequest;
import com.project.twitter.responses.GuestResponse;
import com.project.twitter.responses.UserResponse;
import com.project.twitter.service.UserService;
import com.project.twitter.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
            String authUsername = AuthUtil.getAuthenticatedUsername();

            if (authUsername.equals(username)) {
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
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UpdateUserRequest request){
       try{
           String authUsername = AuthUtil.getAuthenticatedUsername();

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
    public ResponseEntity<List<GuestResponse>> searchUsersByInput(@RequestParam String input) {

        String authUsername = AuthUtil.getAuthenticatedUsername();

        Set<GuestResponse> guestResponses = userService.searchUsersByInput(input);

        guestResponses.removeIf(guestResponse -> guestResponse.getUsername().equals(authUsername));

        return new ResponseEntity<>(new ArrayList<>(guestResponses), HttpStatus.OK);
    }
}
