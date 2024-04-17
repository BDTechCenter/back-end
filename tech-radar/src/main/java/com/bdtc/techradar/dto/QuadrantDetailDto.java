package com.bdtc.techradar.dto;

import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.model.Quadrant;

import java.util.UUID;

public record QuadrantDetailDto(
        UUID id,
        QuadrantEnum name,
        String title,
        String color,
        String txtColor,
        Integer position,
        String description
) {
    public QuadrantDetailDto(Quadrant quadrant) {
        this(
                quadrant.getId(),
                quadrant.getName(),
                quadrant.getTitle(),
                quadrant.getColor(),
                quadrant.getTxtColor(),
                quadrant.getPosition(),
                quadrant.getDescription()
        );
    }
}
