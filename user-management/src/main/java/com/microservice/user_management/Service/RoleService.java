package com.microservice.user_management.Service;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Exceptions.RoleExceptions.RoleNotFoundException;
import com.microservice.user_management.Exceptions.RoleExceptions.RoleNotUpdateToDefault;
import com.microservice.user_management.Exceptions.RoleExceptions.RolesNotFoundException;
import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import com.microservice.user_management.Utils.RoleUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoleService {


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleUtils roleUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);


    @Transactional
    public RoleDTO saveRole(RoleDTO roleDTO) {

        Optional<Role> actualRole = roleRepository.findByName(roleDTO.getRoleName());

        if (actualRole.isPresent()) { throw new IllegalArgumentException("Role ya registrado");}

        Role roleSaved = roleRepository.save(roleMapper.roleDTOToRole(roleDTO));
        RoleDTO savedRoleDTO = roleMapper.roleToRoleDTO(roleSaved);
        LOGGER.info("Role saved successfully: {}", roleDTO);
        return savedRoleDTO;
    }

    @Transactional
    public void deleteRole(RoleDTO roleDTO)  {

        Role actualRole = roleUtils.findRoleOrThrow(roleDTO.getRoleName());
        LOGGER.info("Info of actualRole {}", roleDTO);

        Role defaultRole = roleUtils.findDefaultRoleOrThrow();
        RoleDTO defaultRoleDTO = roleMapper.roleToRoleDTO(defaultRole);

        LOGGER.info("Info of defaultRole {}", defaultRoleDTO);

        Long countUser = roleUtils.countUserByRole(actualRole.getId());
        LOGGER.info("The role {} has {} users", actualRole.getName(), countUser);

        if (countUser > 0) {
            try {
                userRepository.updateRoleToDefault(defaultRole.getId(), actualRole.getId());
                LOGGER.info("{} Users successfully updated to DEFAULT role", countUser);
            } catch (Exception e) {
                LOGGER.error("Users successfully updated to DEFAULT role");
                throw new RoleNotUpdateToDefault();
            }

        }

    }

    public Long getCount(Long idRole) {
           Long countUsers = roleUtils.countUserByRole(idRole);
        LOGGER.info("For de Role with ID {} there are {} users", idRole, countUsers);
           return countUsers;
    }

    public List<RoleDTO> getAll() {
        List<Role> roleList =  roleRepository.findAll();
        if (roleList.isEmpty()) {
            LOGGER.warn("No roles found in the database");
            throw new RolesNotFoundException();
        }
        return roleList.stream()
                .map(roleMapper::roleToRoleDTO)
                .collect(Collectors.toList());
    }



}
