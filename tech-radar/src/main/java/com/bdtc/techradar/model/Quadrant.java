package com.bdtc.techradar.model;

import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.dto.quadrant.QuadrantDto;
import com.bdtc.techradar.dto.quadrant.QuadrantRequestDto;
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

    @Column(unique = true)
//    @Enumerated(EnumType.STRING)
//    private QuadrantEnum name;  // e.g. languages-and-frameworks
    private String name;

    @Column(unique = true)
    private String title;  // e.g. Languages & Frameworks

    // QuadrantMap
    private String color;

    private String txtColor;

    @Column(unique = true)
    private Integer position;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Quadrant(QuadrantRequestDto quadrantRequestDto) {
        this.name = quadrantRequestDto.quadrant().getName();
        this.title = quadrantRequestDto.quadrant().getTitle();
        this.color = quadrantRequestDto.color();
        this.txtColor = quadrantRequestDto.txtColor();
        this.position = quadrantRequestDto.position();
        this.description = quadrantRequestDto.description();
    }
}
