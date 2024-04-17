package com.bdtc.techradar.model;


import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.Ring;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Item {

    @Id
    @GeneratedValue
    private UUID id;

    private String author;

    private String authorEmail;

    private List<String> revisions; // revisions (list of author emails)

    private LocalDateTime creationDate;

    private LocalDateTime publicationDate; // release --> YYYY-MM-DD (confirm format)

    private LocalDateTime updateDate;

    private String name;

    private String title;

    @Enumerated(EnumType.STRING)
    private Flag flag;

    private boolean isActive;  // featured

    @Enumerated(EnumType.STRING)
    private Ring ring;

    @ManyToOne
    @JoinColumn(name = "quadrant_id")
    private Quadrant quadrant;

    @Column(columnDefinition = "TEXT")
    private String body;

}
