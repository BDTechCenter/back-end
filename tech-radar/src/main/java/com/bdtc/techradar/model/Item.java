package com.bdtc.techradar.model;


import com.bdtc.techradar.constant.Expectation;
import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.Ring;
import com.bdtc.techradar.dto.item.ItemRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private boolean needAdminReview = true;

    private String author;
    private String authorEmail;
    private List<String> revisions = new ArrayList<>(); // revisions (list of author emails)

    @Column(unique = true)
    private String title;

    private LocalDate creationDate;
    private LocalDate publicationDate; // release
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
        this.isActive = itemRequestDto.isActive();
        this.title = itemRequestDto.title();
        this.ring = itemRequestDto.ring();
        this.expectation = itemRequestDto.expectation();
        this.body = itemRequestDto.body();
    }

    public void setRevisions(String networkUser) {
        this.revisions.add(networkUser);
    }
}
