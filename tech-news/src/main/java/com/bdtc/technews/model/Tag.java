package com.bdtc.technews.model;

import com.bdtc.technews.dto.tag.TagDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<News> news = new HashSet<>();

    public Tag(TagDto tagDto) {
        this.name = tagDto.tag();
    }

    public Tag(String tagName) {
        this.name = tagName;
    }
}
