package com.bdtc.technews.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "internal-news")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class InternalNews {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime creationDate;

    private LocalDateTime publicationDate;

    private LocalDateTime updateDate;

    @Column(length = 100)
    private String title;

    @Column(length = 255)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String body;

}
