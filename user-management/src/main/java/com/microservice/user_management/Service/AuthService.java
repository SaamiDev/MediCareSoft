package com.microservice.user_management.Service;

import com.microservice.user_management.DTOs.RegisterDTO;
import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Entities.User;
import com.microservice.user_management.Mappers.RegisterMapper;
import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Mappers.UserMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RegisterMapper registerMapper;
    @Autowired
    UserMapper userMapper;


    @Autowired
    RoleMapper roleMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Transactional
    public RegisterDTO register(RegisterDTO registerDTO) {
        // Encuentra el Role por su ID
        Role role = roleRepository.findById(registerDTO.getUserRoleDTO().getRoleId())
                .orElseThrow(() -> {
                    logger.error("Role not found with id: {}", registerDTO.getUserRoleDTO().getRoleId());
                    return new IllegalArgumentException("Role not found");
                });
        logger.info("EL ROL ES {}", roleMapper.roleToRoleDTO(role));
        // Mapea el Role a RoleDTO
        registerDTO.setUserRoleDTO(roleMapper.roleToRoleDTO(role));

        // Convierte el RegisterDTO a User
        User saveUser = registerMapper.registerDTOToUser(registerDTO);
        saveUser.setRole(role);
        logger.info("SE VA A GUARDAR EL USER CON LA INFO {}", userMapper.userToUserDTO(saveUser));
        // Guarda el User
        userRepository.save(saveUser);

        logger.info("User saved successfully {}", registerDTO);
        return registerDTO;
    }



}
