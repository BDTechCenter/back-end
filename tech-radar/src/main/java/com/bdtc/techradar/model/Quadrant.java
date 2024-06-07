package com.bdtc.techradar.model;

import com.bdtc.techradar.dto.quadrant.QuadrantRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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

    @Column(unique = true)
    private Integer position;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Quadrant(QuadrantRequestDto quadrantRequestDto) {
        this.id = quadrantRequestDto.quadrant().getTitle();
        this.name = quadrantRequestDto.name();
        this.title = quadrantRequestDto.title();
        this.color = quadrantRequestDto.color();
        this.position = quadrantRequestDto.position();
        this.description = quadrantRequestDto.description();
    }
}
