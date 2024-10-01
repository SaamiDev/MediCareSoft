package com.microservice.user_management.DTOs;

public class RegisterDTO {

    private String userName;
    private String userSurnames;
    private String userEmail;
    private String userPassword;
    private String userDni;
    private RoleDTO userRoleDTO;

    public RegisterDTO(){}

    public RegisterDTO(String userSurnames, String userName, String userEmail, String userDni, String userPassword, RoleDTO userRoleDTO) {
        this.userSurnames = userSurnames;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userDni = userDni;
        this.userPassword = userPassword;
        this.userRoleDTO = userRoleDTO;
    }

    public RoleDTO getUserRoleDTO() {
        return userRoleDTO;
    }

    public void setUserRoleDTO(RoleDTO userRoleDTO) {
        this.userRoleDTO = userRoleDTO;
    }

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Register {")
                .append(" userName: ").append(userName).append(",")
                .append(" userSurnames: ").append(userSurnames).append(",")
                .append(" userEmail: ").append(userEmail).append(",")
                .append(" userDni: ").append(userDni)
                .append(" userRole: ").append(userRoleDTO.getRoleName())
                .append(" }");

        return sb.toString();
    }
}
