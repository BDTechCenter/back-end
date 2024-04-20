package com.bdtc.techradar.dto.item;

import com.bdtc.techradar.constant.Expectation;
import com.bdtc.techradar.constant.Flag;
import com.bdtc.techradar.constant.QuadrantEnum;
import com.bdtc.techradar.constant.Ring;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ItemRequestDto(
        @NotNull
        Flag flag,

        @NotNull
        boolean isActive,

        @NotBlank
        String authorEmail,

        @NotNull
        List<String> revisions,

        @NotBlank
        String title,

        @NotNull
        Ring ring,

        @NotNull
        Expectation expectation,

        @NotNull
        QuadrantEnum quadrant,

        String body
) {
}
