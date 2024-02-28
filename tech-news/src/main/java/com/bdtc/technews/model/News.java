package com.bdtc.technews.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "internal-news")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class News {

    @Id
    @GeneratedValue
    private UUID id;

    private String author;

    private String collaborators;

    private LocalDateTime creationDate;

    private LocalDateTime publicationDate;

    private LocalDateTime updateDate;

    @Column(length = 100)
    private String title;

    @Column(length = 255)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String body;

    @ManyToMany
    @JoinTable(
            name = "news_tags",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag", referencedColumnName = "name")
    )
    private Set<Tag> tags = new HashSet<>();

    private String source;

}