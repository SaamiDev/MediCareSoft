package com.microservice.user_management.Entities;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public enum RoleType {
    DOCTOR,
    MEDICAL,
    AUXILIARY;

    private static final Set<RoleType> roleTypeSet = EnumSet.allOf(RoleType.class);

    public static boolean AddRole(String roleName) {

        String roleNameUpper = roleName.toUpperCase();

        if(Arrays.stream(RoleType.values()).anyMatch(role -> role.name().equals(roleNameUpper))) {

            return false;
        }

        RoleType newRole = RoleType.valueOf(roleNameUpper);
        roleTypeSet.add(newRole);
        return true;
    }

}



