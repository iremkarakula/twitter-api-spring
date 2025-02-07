package com.project.twitter.service.Impl;

import com.project.twitter.entity.Role;
import com.project.twitter.entity.User;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.requests.LoginRequest;
import com.project.twitter.requests.RegisterRequest;
import com.project.twitter.service.AuthService;
import com.project.twitter.service.RoleService;
import com.project.twitter.util.PhoneAndEmailValidation;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public void register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if(userOptional.isPresent()){
            throw new RuntimeException("Kullanıcı mevcut");
        }
        User user = new User();
        user.setName(request.getName());
        user.setBirthDate(request.getBirthDate());
        user.setUsername(request.getUsername());

        if(PhoneAndEmailValidation.isPhone(request.getContactInfo())){
            Optional<String> e = userRepository.isPhoneUnique(request.getContactInfo());
            if(e.isPresent()){
                throw new IllegalArgumentException("Telefon numarası mevcut");
            }
            user.setPhoneNumber(request.getContactInfo());
        } else if(PhoneAndEmailValidation.isEmail(request.getContactInfo())){
            Optional<String> e = userRepository.isEmailUnique(request.getContactInfo());
            if(e.isPresent()){
                throw new IllegalArgumentException("E-posta mevcut");
            }
            user.setEmail(request.getContactInfo());
        } else {
            throw new IllegalArgumentException("Geçerli email ya da telefon yok");
        }

        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        List<Role> roleList = new ArrayList<>();
        roleService.addUserRole(roleList);
        userRepository.save(user);
    }

    @Override
    public void login(LoginRequest request, HttpSession session) {
        Boolean isAuth = isAuthenticated(request.getUsername(), request.getPassword());
        if(isAuth){
            session.setAttribute("user", request.getUsername());
            return;
        }
        throw new RuntimeException("Giriş yapılamadı");
    }

    @Override
    public Boolean isAuthenticated(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Kullanıcı bulunamadı"));
        if(!user.getUsername().equals(username)){
            throw new UsernameNotFoundException("Kullanıcı bulunamadı");
        }
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Parola yanlış");
        }
        return true;
    }
}
