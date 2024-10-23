package com.microservice.user_management.DTOs;

import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Entities.RoleType;
import com.microservice.user_management.Entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


public class RoleDTO {

    private String roleId;

    @NotBlank(message = "Role name cannot be blank")
    @Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters")
    private String roleName;

    @PastOrPresent(message = "Role date created must be in the past or present")
    private LocalDateTime roleDateCreated;
    public RoleDTO() {}

    public RoleDTO(String roleName) {
        this.roleName = roleName;
    }

    public RoleDTO(LocalDateTime roleDateCreated, String roleId, String roleName) {
        this.roleDateCreated = roleDateCreated;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public LocalDateTime getRoleDateCreated() {
        return roleDateCreated;
    }

    public void setRoleDateCreated(LocalDateTime roleDateCreated) {
        this.roleDateCreated = roleDateCreated;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Role {")
                .append(" roleId = ").append(roleId).append(",")
                .append(" name = ").append(roleName).append(",")
                .append(" dateCreated = ").append(roleDateCreated)
                .append(" }");

        return sb.toString();
    }

}