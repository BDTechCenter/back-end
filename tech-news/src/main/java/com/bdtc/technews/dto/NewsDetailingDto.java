package com.bdtc.technews.dto;

import com.bdtc.technews.model.News;
import com.bdtc.technews.model.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record NewsDetailingDto(
        UUID id,
        String author,
        String creationDate,
        String updateDate,
        String title,
        String body,
        Set<String> tags,
        String imageUrl,
        boolean isPublished
) {
    public NewsDetailingDto(News news, Set<String> tags, String date) {
        this(
                news.getId(),
                news.getAuthor(),
                date,
                date,
                news.getTitle(),
                news.getBody(),
                tags,
                news.getImageUrl(),
                news.isPublished()
        );
    }
}
