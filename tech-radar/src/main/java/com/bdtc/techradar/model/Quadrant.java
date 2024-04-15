package com.bdtc.techradar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "quadrant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Quadrant {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Quadrant name;  // e.g. languages-and-frameworks

    private String title;  // e.g. Languages & Frameworks

    // QuadrantMap
    private String color;

    private String txtColor;

    private Integer position;

    @Column(columnDefinition = "TEXT")
    private String description;
}

// deixar unique
