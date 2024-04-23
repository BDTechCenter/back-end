package com.bdtc.techradar.dto.quadrant;

import com.bdtc.techradar.constant.QuadrantEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuadrantRequestDto(
        @NotNull
        QuadrantEnum quadrant,

        @NotBlank
        String name,

        @NotBlank
        String title,

        @NotBlank
        String color,

        @NotBlank
        String txtColor,

        @NotNull
        Integer position,

        String description
) {
}
