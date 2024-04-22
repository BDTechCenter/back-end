package com.bdtc.technews.service.auth;

import com.bdtc.technews.contants.Roles;
import com.bdtc.technews.dto.user.UserDto;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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