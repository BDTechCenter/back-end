package com.bdtc.techradar.model;

import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.dto.quadrant.QuadrantDto;
import com.bdtc.techradar.dto.quadrant.QuadrantRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "quadrant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Quadrant {

    @Id
    private String id;

    @Column(unique = true)
    private String name;  // e.g. languages-and-frameworks

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
        this.id = quadrantRequestDto.quadrant().getTitle();
        this.name = quadrantRequestDto.name();
        this.title = quadrantRequestDto.title();
        this.color = quadrantRequestDto.color();
        this.txtColor = quadrantRequestDto.txtColor();
        this.position = quadrantRequestDto.position();
        this.description = quadrantRequestDto.description();
    }
}
