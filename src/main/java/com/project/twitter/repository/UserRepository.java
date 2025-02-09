package com.project.twitter.repository;

import com.project.twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u.email from User u where u.email = :email")
    Optional<String> isEmailUnique(String email);

    @Query("select u.phoneNumber from User u where u.phoneNumber = :phone")
    Optional<String> isPhoneUnique(String phone);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:input% OR u.name LIKE %:input%")
    List<User> searchUsersByInput(String input);
}
