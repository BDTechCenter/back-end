package com.bdtc.technews.service.news.utils;

import com.bdtc.technews.contants.RoleOption;
import com.bdtc.technews.dto.user.UserDto;
import com.bdtc.technews.infra.exception.validation.UnauthorizedByRolesException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleAuthHandler {

    private List<RoleOption> validRoles = List.of(RoleOption.ADMIN, RoleOption.BDUSER);

    public void validateUserRole(UserDto userDto) {
        List<String> userRoles = userDto.roles();
        boolean hasAValidRole = false;

        if(userRoles != null && !userRoles.isEmpty()) {
            for(String userRole : userRoles) {
                if(validRoles.contains(RoleOption.stringToRoleOption(userRole))) {
                    hasAValidRole=true;
                    break;
                }
            }
        }
        if(!hasAValidRole) throw new UnauthorizedByRolesException();
    }
}
