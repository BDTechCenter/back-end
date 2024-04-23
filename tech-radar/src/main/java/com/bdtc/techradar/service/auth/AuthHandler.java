package com.bdtc.techradar.service.auth;

import com.bdtc.techradar.constant.Roles;
import com.bdtc.techradar.dto.user.UserDto;
import com.bdtc.techradar.infra.exception.validation.PermissionException;
import com.bdtc.techradar.infra.exception.validation.UnauthorizedByRolesException;
import com.bdtc.techradar.model.Item;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthHandler {
    private final List<Roles> validRoles = List.of(Roles.ADMIN, Roles.BDUSER);

    public void validateUserRole(UserDto userDto) {
        List<Roles> userRoles = userDto.roles();

        if (userRoles != null && !userRoles.isEmpty()) {
            for (Roles userRole : userRoles) {
                if (!validRoles.contains(userRole)) throw new UnauthorizedByRolesException();
            }
        } else {
            throw new UnauthorizedByRolesException();
        }
    }

    public void validateUserIsAdmin(Jwt tokenJWT) {
        UserDto userDto = new UserDto(tokenJWT);
        List<Roles> userRoles = userDto.roles();
        if (!userRoles.contains(Roles.ADMIN)) throw new PermissionException();
    }

    public void validateAuthorOrAdmin(Jwt tokenJWT, Item item) {
        UserDto userDto = new UserDto(tokenJWT);

        boolean isAuthor = userDto.networkUser().equals(item.getAuthorEmail());
        boolean isAdmin = userDto.roles().contains(Roles.ADMIN);

        if (!isAuthor && !isAdmin) throw new PermissionException();
    }
}