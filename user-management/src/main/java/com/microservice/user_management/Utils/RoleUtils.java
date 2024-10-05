package com.microservice.user_management.Utils;

import com.microservice.user_management.Exceptions.RoleExceptions.RoleNotFoundException;
import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Component;
import com.microservice.user_management.Entities.Role;
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

    public Role findDefaultRoleOrThrow() {
        return findRoleOrThrow(ROLE_DEFAULT);
    }

}
