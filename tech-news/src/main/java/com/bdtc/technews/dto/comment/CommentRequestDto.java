package com.bdtc.technews.dto.comment;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(
        @NotBlank
        String comment
) {
}
