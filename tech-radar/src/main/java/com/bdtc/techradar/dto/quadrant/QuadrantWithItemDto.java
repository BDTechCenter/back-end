package com.bdtc.techradar.dto.quadrant;

import com.bdtc.techradar.dto.item.ItemPreviewDto;
import com.bdtc.techradar.model.Quadrant;

import java.util.List;


public record QuadrantWithItemDto(
        String id,
        String name,
        String title,
        String description,
        List<ItemPreviewDto> items
) {
    public QuadrantWithItemDto(Quadrant quadrant, List<ItemPreviewDto> itemsList) {
        this(
                quadrant.getId(),
                quadrant.getName(),
                quadrant.getTitle(),
                quadrant.getDescription(),
                itemsList
        );
    }
}