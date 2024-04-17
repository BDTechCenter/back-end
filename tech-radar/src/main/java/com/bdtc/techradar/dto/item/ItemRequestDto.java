package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.constant.Ring;

import java.util.List;

public record ItemRequestDto(
        Flag flag,
        boolean isActive,
        String authorEmail,
        List<String> revisions,
        String title,
        Ring ring,
        QuadrantEnum quadrant,
        String body
) {
}
