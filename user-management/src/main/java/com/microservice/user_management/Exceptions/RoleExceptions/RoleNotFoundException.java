package com.microservice.user_management.Exceptions.RoleExceptions;

import org.springframework.context.annotation.Role;

public class RoleNotFoundException extends RoleException{

    public RoleNotFoundException(String roleName) {
        super("Role " + roleName + " not found in database");
    }


}
