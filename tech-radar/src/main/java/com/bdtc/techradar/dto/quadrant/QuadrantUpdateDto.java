package com.bdtc.techradar.dto.quadrant;

public record QuadrantUpdateDto(
        String name,
        String title,
        String color,
        String txtColor,
        Integer position,
        String description
) {
}

