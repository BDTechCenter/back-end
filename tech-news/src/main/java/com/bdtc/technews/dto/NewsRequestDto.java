package com.bdtc.technews.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record NewsRequestDto(
        @NotBlank
        String author,
        @NotBlank
        String title,
        @NotBlank
        String summary,
        @NotBlank
        String body,
        @NotEmpty
        Set<String> tags
) {
}
