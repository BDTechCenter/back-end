package com.bdtc.techradar.dto.quadrant;

public record QuadrantUpdateDto(
        String name,
        String title,
        String color,
        Integer position,
        String description
) {
}

