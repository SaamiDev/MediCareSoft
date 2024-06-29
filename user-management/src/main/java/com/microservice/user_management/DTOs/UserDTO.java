package com.microservice.user_management.DTOs;

import java.util.Set;



public class UserDTO {

    private String userId;
    private String userName;
    private String userSurnames;
    private String userEmail;
    private String userDni;
    private RoleDTO roleUserDTO;

    public UserDTO(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDni() {
        return userDni;
    }

    public void setUserDni(String userDni) {
        this.userDni = userDni;
    }

    public RoleDTO getRoleUserDTO() {
        return roleUserDTO;
    }

    public void setRoleUserDTO(RoleDTO roleUserDTO) {
        this.roleUserDTO = roleUserDTO;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSurnames() {
        return userSurnames;
    }

    public void setUserSurnames(String userSurnames) {
        this.userSurnames = userSurnames;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
