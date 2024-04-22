package com.bdtc.technews.contants;

public enum Roles {
    ADMIN,
    BDUSER;

    public static Roles stringToRoleOption(String valor) {
        return Roles.valueOf(valor);
    }
}
