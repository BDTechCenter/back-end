package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.constant.Ring;

import java.util.List;
import java.util.Optional;

public record ItemUpdateDto(
        Flag flag,
        String authorEmail,
        String title,
        Ring ring,

        Optional<QuadrantEnum> quadrant,
        String body
) {
}

