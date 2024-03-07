package com.bdtc.technews.dto;

import com.bdtc.technews.model.NewsBackup;

import java.util.UUID;

public record NewsBackupDto(
        UUID newsId,
        String title,
        String summary,
        String body,
        String imageUrl
) {
    public NewsBackupDto(NewsBackup newsBackup) {
        this(
                newsBackup.getNewsId(),
                newsBackup.getTitle(),
                newsBackup.getSummary(),
                newsBackup.getBody(),
                newsBackup.getImageUrl()
        );
    }
}
