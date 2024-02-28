package com.bdtc.technews.dto;

import com.bdtc.technews.model.Tag;
import jakarta.validation.constraints.NotBlank;

public record TagDto(
        @NotBlank
        String tag
) {
        public TagDto(Tag tag) {
                this(tag.getName());
        }
}
