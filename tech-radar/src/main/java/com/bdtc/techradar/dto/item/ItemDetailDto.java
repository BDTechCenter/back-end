package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.constant.Ring;
import com.bdtc.techradar.model.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ItemDetailDto(
        UUID id,
        Flag flag,
        boolean isActive,
        String authorEmail,
        List<String> revisions,
        String title,
        LocalDateTime creationDate,
        LocalDateTime publicationDate,
        Ring ring,
        String quadrantId,
        String body
) {
    public ItemDetailDto(Item item) {
        this(
                item.getId(),
                item.getFlag(),
                item.isActive(),
                item.getAuthorEmail(),
                item.getRevisions(),
                item.getTitle(),
                item.getCreationDate(),
                item.getPublicationDate(),
                item.getRing(),
                item.getQuadrant().getId(),
                item.getBody());
    }
}
