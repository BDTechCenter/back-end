package com.bdtc.techradar.dto.quadrant;

import com.bdtc.techradar.constant.QuadrantEnum;

public record QuadrantUpdateDto(
        QuadrantEnum quadrant,
        String color,
        String txtColor,
        Integer position,
        String description
) {
}

