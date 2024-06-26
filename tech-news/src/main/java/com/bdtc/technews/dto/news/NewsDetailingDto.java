package com.bdtc.technews.dto.news;

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
        Long views,
        int upVotes,
        String imageUrl,
        boolean isPublished
) {
    public NewsDetailingDto(News news, Set<String> tags, String creationDate, String updateDate) {
        this(
                news.getId(),
                news.getAuthor(),
                creationDate,
                updateDate,
                news.getTitle(),
                news.getBody(),
                tags,
                news.getViews(),
                news.getUpVotes(),
                news.getImageUrl(),
                news.isPublished()
        );
    }
}
