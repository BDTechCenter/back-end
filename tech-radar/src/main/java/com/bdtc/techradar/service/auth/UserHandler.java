package com.bdtc.techradar.service.auth;

import com.bdtc.techradar.constant.Roles;
import com.bdtc.techradar.dto.user.UserDto;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserHandler {

    public UserDto getUserByTokenJWT(Jwt jwt) {
        return new UserDto(
                jwt.getClaimAsString("name"),
                jwt.getClaimAsString("preferred_username"),
                Roles.arrayListToRoleList(
                        (ArrayList<String>) jwt.getClaimAsStringList("roles")
                )
        );
    }
}