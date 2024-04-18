package com.bdtc.techradar.dto.quadrant;

import com.bdtc.techradar.constant.QuadrantEnum;

public record QuadrantUpdateDto(
        String name,
        String title,
        String color,
        String txtColor,
        Integer position,
        String description
) {
}

