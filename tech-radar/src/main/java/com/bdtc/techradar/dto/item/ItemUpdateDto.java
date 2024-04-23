package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.constant.Expectation;
import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.constant.Ring;

import java.util.Optional;

public record ItemUpdateDto(
        String title,
        Ring ring,
        Expectation expectation,

        Optional<QuadrantEnum> quadrant,
        String body
) {
}

