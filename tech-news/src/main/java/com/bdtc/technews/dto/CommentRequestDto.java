package com.bdtc.technews.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(
        @NotBlank
        String author,

        @NotBlank
        String comment
) {
}
