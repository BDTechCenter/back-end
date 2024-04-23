package com.bdtc.technews.dto.news;

import com.bdtc.technews.model.News;

import java.util.Set;
import java.util.UUID;

public record NewsDetailingWUpVoteDto(
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
        boolean isPublished,
        boolean alreadyUpVoted
) {
    public NewsDetailingWUpVoteDto(News news, Set<String> tags, String date, boolean alreadyUpVoted) {
        this(
                news.getId(),
                news.getAuthor(),
                date,
                date,
                news.getTitle(),
                news.getBody(),
                tags,
                news.getViews(),
                news.getUpVotes(),
                news.getImageUrl(),
                news.isPublished(),
                alreadyUpVoted
        );
    }
}
