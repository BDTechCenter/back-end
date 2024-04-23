package com.bdtc.technews.service.auth;

import com.bdtc.technews.contants.Roles;
import com.bdtc.technews.dto.user.UserDto;
import com.bdtc.technews.infra.exception.validation.PermissionException;
import com.bdtc.technews.infra.exception.validation.UnauthorizedByRolesException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorizationHandler {
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

    public boolean userHasAuthorization(UserDto userDto, String authorEmail) {
        boolean isAuthor = userDto.networkUser().equals(authorEmail);
        boolean isAdmin = userDto.roles().contains(Roles.ADMIN);

        if (!isAuthor && !isAdmin) throw new PermissionException();
        return true;
    }

}
