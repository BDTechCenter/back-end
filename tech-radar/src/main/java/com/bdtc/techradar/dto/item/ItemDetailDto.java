package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.Ring;
import com.bdtc.techradar.model.Item;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ItemDetailDto(
        UUID id,
        Flag flag,
        boolean isActive,
        boolean needAdminReview,
        String authorEmail,
        List<String> revisions,
        String title,
        LocalDate creationDate,
        LocalDate publicationDate,
        LocalDate updateDate,
        Ring ring,
        String expectation,
        String quadrantId,
        String body
) {
    public ItemDetailDto(Item item) {
        this(
                item.getId(),
                item.getFlag(),
                item.isActive(),
                item.isNeedAdminReview(),
                item.getAuthorEmail(),
                item.getRevisions(),
                item.getTitle(),
                item.getCreationDate(),
                item.getPublicationDate(),
                item.getUpdateDate(),
                item.getRing(),
                item.getExpectation().getDescription(),
                item.getQuadrant().getId(),
                item.getBody());
    }
}
