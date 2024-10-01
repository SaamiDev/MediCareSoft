package com.microservice.user_management.Service;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Entities.RoleType;
import com.microservice.user_management.Entities.User;
import com.microservice.user_management.Exceptions.RoleNotFoundException;
import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Mappers.UserMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import com.microservice.user_management.Utils.RoleUtils;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);


    @Transactional
    public RoleDTO saveRole(RoleDTO roleDTO) {

        Optional<Role> actualRole = roleRepository.findByName(roleDTO.getRoleName());

        if (actualRole.isPresent()) { throw new IllegalArgumentException("Role ya registrado");}

        Role roleSaved = roleRepository.save(roleMapper.roleDTOToRole(roleDTO));
        RoleDTO savedRoleDTO = roleMapper.roleToRoleDTO(roleSaved);
        logger.info("Role saved successfully: {}", roleDTO);
        return savedRoleDTO;
    }

    @Transactional
    public void deleteRole(RoleDTO roleDTO) {
        Role actualRole = roleMapper.roleDTOToRole(roleDTO);
        Optional<Role> defaultRole = roleRepository.findByName("DEFAULT");


        // Esto verifica si el rol existe
        roleRepository.findById(actualRole.getId())
                .orElseThrow(() -> {
                    logger.error("Error deleting role because not exist nothing whith Id: {}", actualRole.getRoleId());
                    return new RoleNotFoundException("Error: Role with ID " + actualRole.getRoleId() + " does not exist.");
                });

        Long countUser = roleUtils.countUserByRole(actualRole.getId());

        if(countUser > 0 ) {
            userRepository.updateRoleToDefault(defaultRole.get().getId(), actualRole.getId());
        }

        //roleRepository.deleteByRoleId(idRole);
        //logger.info("Role whith Id {} deleted successfully", idRole);
    }

    public Long getCount(Long idRole) {
           Long countUsers = roleUtils.countUserByRole(idRole);
           logger.info("For de Role with ID {} there are {} users", idRole, countUsers);
           return countUsers;
    }

    public List<RoleDTO> getAll() {
        List<Role> roleList =  roleRepository.findAll();
        if (roleList.isEmpty()) {
            logger.warn("No roles found in the database");
            throw new RoleNotFoundException("No roles found in the database");
        }
        return roleList.stream()
                .map(roleMapper::roleToRoleDTO)
                .collect(Collectors.toList());
    }



}
