package com.bdtc.techradar.service.auth;

import com.bdtc.techradar.constant.Roles;
import com.bdtc.techradar.dto.user.UserDto;
import com.bdtc.techradar.infra.exception.validation.UnauthorizedByRolesException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RoleAuthHandler {
    private final List<Roles> validRoles = List.of(Roles.ADMIN, Roles.BDUSER);

    public void validateUserRole(UserDto userDto) {
        List<Roles> userRoles = userDto.roles();

        if(userRoles != null && !userRoles.isEmpty()) {
            for(Roles userRole : userRoles) {
                if(!validRoles.contains(userRole)) throw new UnauthorizedByRolesException();
            }
        } else {
            throw new UnauthorizedByRolesException();
        }
    }
}