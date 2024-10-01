package com.microservice.user_management.Utils;

import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RoleUtils {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoleMapper roleMapper;

    private static final Logger logger = LoggerFactory.getLogger(RoleUtils.class);

    public Long countUserByRole(Long idRole) {

        return userRepository.countUserByRole(idRole);

    }



}
