package com.bdtc.techradar.dto.user;

import java.util.List;

public record UserDto(
        String username,
        String networkUser,
        List<String> roles
) {
}