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
        LocalDateTime creationDate,
        LocalDateTime updateDate,
        String title,
        String summary,
        String body,
        Set<String> tags
) {
    public NewsDetailingDto(News news, Set<String> tags) {
        this(
                news.getId(),
                news.getAuthor(),
                news.getCreationDate(),
                news.getUpdateDate(),
                news.getTitle(),
                news.getSummary(),
                news.getBody(),
                tags
        );
    }
}
