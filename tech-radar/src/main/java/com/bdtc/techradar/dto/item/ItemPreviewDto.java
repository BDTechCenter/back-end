package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.constant.Ring;
import com.bdtc.techradar.model.Item;

import java.util.UUID;

public record ItemPreviewDto(
        UUID id,
        String title,
        Ring ring,
        UUID quadrantId
) {
    public ItemPreviewDto(Item item) {
        this(item.getId(), item.getTitle(), item.getRing(), item.getQuadrant().getId());
    }
}
