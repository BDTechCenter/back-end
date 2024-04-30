package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.Ring;
import com.bdtc.techradar.model.Item;

import java.util.UUID;

public record ItemPreviewDto(
        UUID id,
        Flag flag,
        String title,
        Ring ring,
        String expectation,
        String quadrantId,
        boolean isActive
) {
    public ItemPreviewDto(Item item) {
        this(item.getId(), item.getFlag(), item.getTitle(), item.getRing(), item.getExpectation().getDescription(), item.getQuadrant().getId(), item.isActive());
    }
}
