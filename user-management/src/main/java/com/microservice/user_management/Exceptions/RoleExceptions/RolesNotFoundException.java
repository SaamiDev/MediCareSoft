package com.microservice.user_management.Exceptions.RoleExceptions;

public class RolesNotFoundException extends RoleException {

    public RolesNotFoundException() {
        super("No roles found in the database");
    }
}
