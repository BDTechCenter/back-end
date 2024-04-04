package com.bdtc.technews.service.news.utils;

import com.bdtc.technews.dto.UserDto;
import com.bdtc.technews.infra.exception.validation.UnauthorizedByRoles;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleAuthHandler {

    private List<String> validRoles = List.of("ADMIN", "BDUSER");

    public void validateUserRole(UserDto userDto) {
        List<String> userRoles = userDto.roles();
        boolean hasAValidRole = false;

        if(userRoles != null && userRoles.isEmpty()) {
            for(String userRole : userRoles) {
                if(validRoles.contains(userRole)) {
                    hasAValidRole=true;
                    break;
                }
            }
        }
        if(!hasAValidRole) throw new UnauthorizedByRoles();
    }
}
