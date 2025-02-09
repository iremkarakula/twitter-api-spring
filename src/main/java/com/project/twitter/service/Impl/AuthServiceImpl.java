package com.project.twitter.service.Impl;

import com.project.twitter.entity.Role;
import com.project.twitter.entity.User;
import com.project.twitter.repository.UserRepository;
import com.project.twitter.requests.LoginRequest;
import com.project.twitter.requests.RegisterRequest;
import com.project.twitter.service.AuthService;
import com.project.twitter.service.RoleService;
import com.project.twitter.util.PhoneAndEmailValidation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public ResponseEntity<String> register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if(userOptional.isPresent()){
            return new ResponseEntity<>("Kullanıcı adı kayıtlı", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(request.getName());
        user.setBirthDate(request.getBirthDate());
        user.setUsername(request.getUsername());

        if(PhoneAndEmailValidation.isPhone(request.getContactInfo())){
            Optional<String> e = userRepository.isPhoneUnique(request.getContactInfo());
            if(e.isPresent()){
                return new ResponseEntity<>("Telefon numarası kayıtlı", HttpStatus.BAD_REQUEST);
            }
            user.setPhoneNumber(request.getContactInfo());
        } else if(PhoneAndEmailValidation.isEmail(request.getContactInfo())){
            Optional<String> e = userRepository.isEmailUnique(request.getContactInfo());
            if(e.isPresent()){
                return new ResponseEntity<>("Eposta kayıtlı", HttpStatus.BAD_REQUEST);
            }
            user.setEmail(request.getContactInfo());
        } else {
            return new ResponseEntity<>("Geçerli bir eposta ya da telefon giriniz", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        List<Role> roleList = new ArrayList<>();
        roleService.addUserRole(roleList);
        userRepository.save(user);

        return new ResponseEntity<>("Kayıt oldunuz", HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<String> login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("Kullanıcı bulunamadı", HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Şifre Hatalı", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return new ResponseEntity<>("Giriş başarılı!", HttpStatus.OK);
    }


}
