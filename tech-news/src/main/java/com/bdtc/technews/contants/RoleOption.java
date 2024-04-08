package com.bdtc.technews.contants;

public enum RoleOption {
    ADMIN,
    BDUSER;

    public static RoleOption stringToRoleOption(String valor) {
        return RoleOption.valueOf(valor);
    }
}
