package com.bdtc.techradar.model;


import com.bdtc.techradar.constant.Expectation;
import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.Ring;
import com.bdtc.techradar.dto.item.ItemRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Flag flag;

    private boolean isActive;  // featured

    private String author;
    private String authorEmail;
    private List<String> revisions; // revisions (list of author emails)

    @Column(unique = true)
    private String title;

    private LocalDate creationDate;
    private LocalDate publicationDate; // release --> YYYY-MM-DD (confirm format)
    private LocalDate updateDate;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Ring ring;

    @Enumerated(EnumType.STRING)
    private Expectation expectation;

    @ManyToOne
    @JoinColumn(name = "quadrant_id")
    private Quadrant quadrant;

    @Column(columnDefinition = "TEXT")
    private String body;

    public Item(ItemRequestDto itemRequestDto) {
        this.flag = itemRequestDto.flag();
        this.isActive = itemRequestDto.isActive();
        this.authorEmail = itemRequestDto.authorEmail();
        this.revisions = itemRequestDto.revisions();
        this.title = itemRequestDto.title();
        this.ring = itemRequestDto.ring();
        this.expectation = itemRequestDto.expectation();
        this.body = itemRequestDto.body();
    }
}
