package com.microservice.user_management.DTOs;

import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Entities.RoleType;
import com.microservice.user_management.Entities.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


public class RoleDTO {

    private String roleId;
    private String roleName;
    private LocalDateTime roleDateCreated;

    public RoleDTO() {}



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