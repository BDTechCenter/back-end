package com.bdtc.techradar.dto.quadrant;

import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.model.Quadrant;

public record QuadrantRequestDto(
        QuadrantEnum quadrant,
        String color,
        String txtColor,
        Integer position,
        String description
) {
}
