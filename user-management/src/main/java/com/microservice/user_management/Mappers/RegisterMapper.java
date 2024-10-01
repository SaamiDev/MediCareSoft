package com.microservice.user_management.Mappers;

import com.microservice.user_management.DTOs.RegisterDTO;
import com.microservice.user_management.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface RegisterMapper {

    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    @SuppressWarnings("unmappedTargetProperties")
    @Mapping(target = "userName", source = "name")
    @Mapping(target = "userSurnames", source = "surnames")
    @Mapping(target = "userEmail", source = "email")
    @Mapping(target = "userPassword", source = "password")
    @Mapping(target = "userDni", source = "dni")
    @Mapping(target = "userRoleDTO", source = "role", qualifiedByName = "roleToRoleDTO")
    RegisterDTO userToRegisterDT0(User user);

    @SuppressWarnings("unmappedTargetProperties")
    @Mapping(target = "name", source = "userName")
    @Mapping(target = "surnames", source = "userSurnames")
    @Mapping(target = "email", source = "userEmail")
    @Mapping(target = "password", source = "userPassword")
    @Mapping(target = "dni", source = "userDni")
    @Mapping(target = "role", source = "userRoleDTO", qualifiedByName = "roleDTOToRole")
    User registerDTOToUser(RegisterDTO registerDTO);
}
