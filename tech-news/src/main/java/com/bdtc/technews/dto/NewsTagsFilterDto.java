package com.bdtc.technews.dto;

import java.util.List;

public record NewsTagsFilterDto(
        List<String> tags
) {
}
