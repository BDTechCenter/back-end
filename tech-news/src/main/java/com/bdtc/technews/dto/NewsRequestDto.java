package com.bdtc.technews.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public record NewsRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String body,
        @NotEmpty
        Set<String> tags,
        MultipartFile image,
        String isPublished
) {
        public NewsRequestDto {
                if(isPublished == null) isPublished="true";
        }
}
