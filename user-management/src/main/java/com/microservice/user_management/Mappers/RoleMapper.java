package com.microservice.user_management.Mappers;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @SuppressWarnings("unmappedTargetProperties")
    @Mapping(target = "roleId", source = "roleId")
    @Mapping(target = "roleName", source = "name")
    @Mapping(target = "roleDateCreated", source = "dateCreated")
    RoleDTO roleToRoleDTO(Role role);

    @SuppressWarnings("unmappedTargetProperties")
    @Mapping(target = "roleId", source = "roleId")
    @Mapping(target = "name", source = "roleName")
    @Mapping(target = "dateCreated", source = "roleDateCreated")
    Role roleDTOToRole(RoleDTO roleDTO);
}
