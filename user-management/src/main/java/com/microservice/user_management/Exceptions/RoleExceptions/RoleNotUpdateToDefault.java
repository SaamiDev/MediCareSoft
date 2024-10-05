package com.microservice.user_management.Exceptions.RoleExceptions;

public class RoleNotUpdateToDefault extends RoleException{

        public RoleNotUpdateToDefault() {
            super("Users could not be updated to the DEFAULT role");
        }
}
