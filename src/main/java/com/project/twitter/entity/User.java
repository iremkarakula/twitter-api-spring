package com.project.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", schema = "twitter")
public class User extends Identifier{

    @Column(name = "name")
    @Size(max = 50, min = 3)
    @NotBlank(message = "İsim boş bırakılamaz")
    private String name;

    @Column(name = "birth_date")
    @NotNull(message = "Doğum tarihi boş bırakılamaz")
    private Date birthDate;

    @Column(name = "username", unique = true)
    @NotBlank(message = "Kullanıcı adı boş bırakılamaz")
    @Size(max=15, message = "Kullanıcı adı en fazla 15 karakter olabilir")
    private String username;

    @Column(name = "email", nullable = true, unique = true)
    @Size(max=320, message = "Email en fazla 320 karakter olabilir")
    private String email;

    @Column(name="phone_number", nullable = true, unique = true)
    @Size(min=10, max=10, message = "Telefon numarası 10 karakter olmalıdır")
    private String phoneNumber;

    @Column(name = "password")
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tweet> tweets;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="follow", schema = "twitter",
    joinColumns = @JoinColumn(name = "following_id"),
    inverseJoinColumns = @JoinColumn(name = "followers_id"))
    private Set<User> followings;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="follow", schema = "twitter",
            joinColumns = @JoinColumn(name = "followers_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> followers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Repost> reposts;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<TweetLike> tweetLikes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<CommentLike> commentLikes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", schema = "twitter",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Column(name = "bio", nullable = true)
    @Size(max=160)
    private String bio;

    @Column(name = "website", nullable = true)
    @Size(max=100)
    private String website;

    @Column(name = "location", nullable = true)
    @Size(max=30)
    private String location;


}
