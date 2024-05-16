package com.bdtc.techradar.dto.quadrant;

import com.bdtc.techradar.model.Quadrant;

public record QuadrantDetailDto(
        String id,
        String name,
        String title,
        String color,
        Integer position,
        String description
) {
    public QuadrantDetailDto(Quadrant quadrant) {
        this(
                quadrant.getId(),
                quadrant.getName(),
                quadrant.getTitle(),
                quadrant.getColor(),
                quadrant.getPosition(),
                quadrant.getDescription()
        );
    }
}
