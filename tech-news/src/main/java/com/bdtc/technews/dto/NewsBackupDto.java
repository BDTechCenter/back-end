package com.bdtc.technews.dto;

import com.bdtc.technews.model.NewsBackup;

import java.util.UUID;

public record NewsBackupDto(
        Long id,
        UUID newsId,
        String title,
        String body,
        String imageUrl
) {
    public NewsBackupDto(NewsBackup newsBackup) {
        this(
                newsBackup.getId(),
                newsBackup.getNewsId(),
                newsBackup.getTitle(),
                newsBackup.getBody(),
                newsBackup.getImageUrl()
        );
    }
}
