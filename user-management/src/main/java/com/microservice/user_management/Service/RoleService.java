package com.microservice.user_management.Service;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Entities.RoleType;
import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Mappers.UserMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import jakarta.transaction.Transactional;
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
    RoleMapper roleMapper;

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);


    @Transactional
    public RoleDTO saveRole(RoleDTO roleDTO) {

        Optional<Role> actualRole= roleRepository.findByName(roleDTO.getRoleName());

        if (actualRole.isPresent()) { throw new IllegalArgumentException("Role ya registrado");}

        Role roleSaved = roleRepository.save(roleMapper.roleDTOToRole(roleDTO));
        RoleDTO savedRoleDTO = roleMapper.roleToRoleDTO(roleSaved);
        logger.info("Role saved successfully: {}", roleDTO);
        return savedRoleDTO;
    }

    @Transactional
    public void deleteRole(String idRole) {
        roleRepository.findById(idRole)
                .orElseThrow(() -> {
                    logger.error("Error deleting role because not exist nothing whith Id: {}", idRole);
                    throw new IllegalArgumentException("Error deleting role because not exist");
                });
        logger.info("Role whith Id {} deleted successfully", idRole);
        roleRepository.deleteByRoleId(idRole);
    }

    public List<RoleDTO> getAll() {
        List<Role> roleList =  roleRepository.findAll();
        if (roleList.isEmpty()) {
            logger.warn("No roles found in the database");
            throw new IllegalArgumentException("No roles found");
        }
        return roleList.stream()
                .map(roleMapper::roleToRoleDTO)
                .collect(Collectors.toList());
    }

}
