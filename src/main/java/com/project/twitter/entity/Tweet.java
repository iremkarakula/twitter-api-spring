package com.project.twitter.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tweet", schema = "twitter")
public class Tweet extends Identifier{

    @Column(name = "text")
    @Size(max=280, message = "Tweet en fazla 280 karakter olabilir")
    private String text;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tweet")
    private Set<TweetLike> likes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tweet")
    private Set<Repost> reposts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tweet")
    private List<Comment> comments;

    public Tweet() {
        this.likes = new HashSet<>();
        this.reposts = new HashSet<>();
        this.comments = new ArrayList<>();
    }


}
