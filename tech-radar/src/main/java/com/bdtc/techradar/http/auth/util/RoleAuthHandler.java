package com.bdtc.techradar.http.auth.util;

import com.bdtc.techradar.constant.Roles;
import com.bdtc.techradar.infra.exception.validation.UnauthorizedByRolesException;
import org.springframework.stereotype.Component;
import com.bdtc.techradar.dto.user.UserDto;
import java.util.List;

@Component
public class RoleAuthHandler {

    private final List<String> validRoles = List.of(Roles.ADMIN.getRoleValue(), Roles.BDUSER.getRoleValue());

    public void validateUserRole(UserDto userDto) {
        List<String> userRoles = userDto.roles();

        if(userRoles != null && !userRoles.isEmpty()) {
            for(String userRole : userRoles) {
                // case role not in valid roles
                if(!validRoles.contains(userRole)) throw new UnauthorizedByRolesException();
            }
        }
    }
}
