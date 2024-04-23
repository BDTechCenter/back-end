package com.bdtc.techradar.dto.user;

import com.bdtc.techradar.constant.Roles;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;

public record UserDto(
        String username,
        String networkUser,
        List<Roles> roles
) {
    public UserDto(Jwt jwt) {
        this(
                jwt.getClaimAsString("name"),
                jwt.getClaimAsString("preferred_username"),
                Roles.arrayListToRoleList(
                        (ArrayList<String>) jwt.getClaimAsStringList("roles")
                )
        );
    }
}