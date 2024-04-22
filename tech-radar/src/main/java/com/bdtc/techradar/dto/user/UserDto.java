package com.bdtc.techradar.dto.user;

import com.bdtc.techradar.constant.Roles;

import java.util.List;

public record UserDto(
        String username,
        String networkUser,
        List<Roles> roles
) {
}