package com.bdtc.techradar.dto.quadrant;

import com.bdtc.techradar.constant.QuadrantEnum;

public record QuadrantRequestDto(
        QuadrantEnum quadrant,
        String name,
        String title,
        String color,
        String txtColor,
        Integer position,
        String description
) {
}