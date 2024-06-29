package com.microservice.user_management.Service;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Entities.RoleType;
import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Mappers.UserMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleService {


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Transactional
    public RoleDTO saveRole(RoleDTO roleDTO) {

        Optional<Role> actualRole= roleRepository.findByName(roleDTO.getRoleName());

        if (actualRole.isPresent()) { throw new IllegalArgumentException("Role ya registrado");}

        Role roleSaved = roleRepository.save(roleMapper.roleDTOToRole(roleDTO));
        RoleDTO savedRoleDTO = roleMapper.roleToRoleDTO(roleSaved);

        return savedRoleDTO;
    }

    @Transactional
    public void deleteRole(String idRole) {


        roleRepository.deleteByRoleId(idRole);
    }

    //private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
}
