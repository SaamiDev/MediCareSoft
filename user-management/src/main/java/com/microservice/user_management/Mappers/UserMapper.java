package com.microservice.user_management.Mappers;

import com.microservice.user_management.DTOs.UserDTO;
import com.microservice.user_management.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @SuppressWarnings("unmappedTargetProperties")
    @Mapping(target = "userId", source = "user_id")
    @Mapping(target = "userName", source = "name")
    @Mapping(target = "userSurnames", source = "surnames")
    @Mapping(target = "userEmail", source = "email")
    @Mapping(target = "userDni", source = "dni")
    @Mapping(target = "roleUserDTO", source = "role") // Nombre corregido
    UserDTO userToUserDTO(User user);

    @SuppressWarnings("unmappedTargetProperties")
    @Mapping(target = "user_id", source = "userId")
    @Mapping(target = "name", source = "userName")
    @Mapping(target = "surnames", source = "userSurnames")
    @Mapping(target = "email", source = "userEmail")
    @Mapping(target = "dni", source = "userDni")
    @Mapping(target = "role", source = "roleUserDTO") // Nombre corregido
    User userDTOToUser(UserDTO userDTO);
}
