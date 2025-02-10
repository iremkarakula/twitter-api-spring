package com.project.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "comment", schema = "twitter")
public class Comment extends Identifier{

    @Column(name = "text")
    @Size(min=1, max = 280, message = "Yorum en fazla 280 karakter olabilir")
    @NotBlank(message = "Yorum boş olamaz")
    private String text;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment")
    private Set<CommentLike> likes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment")
    private Set<CommentRepost> reposts;

    public Comment() {
        this.likes = new HashSet<>();
        this.reposts = new HashSet<>();
    }


}
