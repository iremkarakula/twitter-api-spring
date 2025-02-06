package com.project.twitter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Identifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "record_time")
    @CreationTimestamp
    private Date recordTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return id == that.id;
    }
}
