package com.bdtc.technews.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NewsPreviewDto(
        UUID id,
        LocalDateTime updateDate,
        String title,
        String author
) {
}
