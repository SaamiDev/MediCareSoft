package com.microservice.user_management.Utils;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Exceptions.RoleExceptions.RoleException;
import com.microservice.user_management.Exceptions.RoleExceptions.RoleNotFoundException;
import com.microservice.user_management.Exceptions.RoleExceptions.RolesNotFoundException;
import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Component;
import com.microservice.user_management.Entities.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoleUtils {

    final static String ROLE_DEFAULT = "DEFAULT";

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public RoleMapper roleMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleUtils.class);



    public Long countUserByRole(Long idRole) { return userRepository.countUserByRole(idRole); }

    public Role findRoleOrThrow(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
    }

    public Role findRoleByIdOrThrow(String externalId) {
        return roleRepository.findById(externalId)
                .orElseThrow(() ->
                        new RoleException("Role not found in the database"));
    }

    public List<Role> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        if (roleList.isEmpty()) {
            LOGGER.warn("No roles found in the database");
            throw new RolesNotFoundException();
        }
        return roleList;
    }

    public Role findDefaultRoleOrThrow() {
        return findRoleOrThrow(ROLE_DEFAULT);
    }

}
