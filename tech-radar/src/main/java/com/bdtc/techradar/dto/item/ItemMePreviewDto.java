package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.model.Item;

import java.util.UUID;

public record ItemMePreviewDto(
        UUID id,
        String title,
        String authorEmail,
        boolean isActive,
        boolean needAdminReview
) {
    public ItemMePreviewDto(Item item) {
        this(item.getId(), item.getTitle(), item.getAuthorEmail(), item.isActive(), item.isNeedAdminReview());
    }
}
