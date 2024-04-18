package com.bdtc.techradar.dto.quadrant;

import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.model.Quadrant;

import java.util.UUID;

public record QuadrantDetailDto(
        String id,
        String name,
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
