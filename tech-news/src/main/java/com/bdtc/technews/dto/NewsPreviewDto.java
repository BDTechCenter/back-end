package com.bdtc.technews.dto;

import com.bdtc.technews.model.News;

import java.time.LocalDateTime;
import java.util.UUID;

public record NewsPreviewDto(
        UUID id,
        String updateDate,
        String title,
        String author,
        String imageUrl
) {
    public NewsPreviewDto(News news, String date) {
        this(
                news.getId(),
                date,
                news.getTitle(),
                news.getAuthor(),
                news.getImageUrl()
        );
    }
}
