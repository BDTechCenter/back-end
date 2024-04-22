package com.bdtc.techradar.constant;

import java.util.ArrayList;
import java.util.List;

public enum Roles {
    ADMIN,
    BDUSER;

    public static List<Roles> arrayListToRoleList(ArrayList<String> rolesStringList) {
        List<Roles> rolesList = new ArrayList<Roles>();

        if(rolesStringList != null && !rolesStringList.isEmpty()){
            for(String role : rolesStringList) {
                rolesList.add(Roles.valueOf(role));
            }
        }
        return rolesList;
    }
}
