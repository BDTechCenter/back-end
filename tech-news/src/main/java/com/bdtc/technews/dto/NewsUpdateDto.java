package com.bdtc.technews.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public record NewsUpdateDto(
        String title,
        String summary,
        String body,
        Set<String> tags,
        MultipartFile image
) {
}
