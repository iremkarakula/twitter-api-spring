package com.project.twitter.util;

import com.project.twitter.entity.User;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.requests.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class PhoneAndEmailValidation {

    private final UserRepository userRepository;

    public static boolean isPhone(String contact) {
        String phoneRegex = "^[1-9]\\d{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }


    public static boolean isEmail(String contact) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }

    public void updateEmailValidation(UpdateUserRequest request, User user){
        if (!request.getEmail().isEmpty()) {

            if (!PhoneAndEmailValidation.isEmail(request.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Geçerli bir e-posta giriniz.");
            }

            if (user.getEmail().equals(request.getEmail())) {

                user.setEmail(request.getEmail());
            } else {
                Optional<String> isEmailUnique = userRepository.isEmailUnique(request.getEmail());
                if (isEmailUnique.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-posta adresi kayıtlı.");

                } else {
                    user.setEmail(request.getEmail());
                }
            }

        }
    }

    public void updatePhoneValidation(UpdateUserRequest request, User user){
        if (!request.getPhoneNumber().isEmpty()) {

            if (!PhoneAndEmailValidation.isPhone(request.getPhoneNumber())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Geçerli telefon numarası giriniz.");
            }

            if (user.getPhoneNumber().equals(request.getPhoneNumber())) {

                user.setPhoneNumber(request.getPhoneNumber());
            } else {
                Optional<String> isPhoneUnique = userRepository.isPhoneUnique(request.getPhoneNumber());
                if (isPhoneUnique.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefon numarası kayıtlı.");

                } else {
                    user.setPhoneNumber(request.getPhoneNumber());
                }
            }

        }
    }
}
