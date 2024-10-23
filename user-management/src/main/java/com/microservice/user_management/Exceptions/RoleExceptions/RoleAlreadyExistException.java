package com.microservice.user_management.Exceptions.RoleExceptions;

public class RoleAlreadyExistException extends RoleException {

    public RoleAlreadyExistException(String roleName) {
        super("Role already registered with name: " + roleName);
    }
}
