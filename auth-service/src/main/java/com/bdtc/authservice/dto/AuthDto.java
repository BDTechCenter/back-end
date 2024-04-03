package com.bdtc.authservice.dto;

import com.bdtc.authservice.model.Auth;

import java.util.ArrayList;

public record AuthDto(
        String networkUser,
        String username,
        ArrayList<String> roles
) {
    public AuthDto(Auth auth) {
        this(auth.getNetwork_name(), auth.getUsername(), auth.getRoles());
    }
}
