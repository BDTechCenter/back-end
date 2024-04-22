package com.bdtc.technews.dto.user;

import com.bdtc.technews.contants.Roles;

import java.util.List;

public record UserDto(
        String username,
        String networkUser,
        List<Roles> roles
) {
}
