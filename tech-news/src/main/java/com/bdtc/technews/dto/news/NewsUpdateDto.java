package com.bdtc.technews.dto.news;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public record NewsUpdateDto(
        String title,
        String body,
        Set<String> tags,
        MultipartFile image
) {
}
