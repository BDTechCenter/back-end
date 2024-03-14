package com.bdtc.authservice.dto;

import com.bdtc.authservice.model.Auth;

public record AuthDto(
        String network_user,
        String username
) {
    public AuthDto(Auth auth) {
        this(auth.getNetwork_name(), auth.getUsername());
    }
}
