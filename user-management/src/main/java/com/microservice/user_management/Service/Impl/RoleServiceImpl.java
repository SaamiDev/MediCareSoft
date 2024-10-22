package com.microservice.user_management.Service.Impl;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Exceptions.RoleExceptions.RoleNotUpdateToDefault;
import com.microservice.user_management.Mappers.RoleMapper;
import com.microservice.user_management.Repositories.RoleRepository;
import com.microservice.user_management.Repositories.UserRepository;
import com.microservice.user_management.Service.RoleService;
import com.microservice.user_management.Utils.RoleUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final RoleUtils roleUtils;


    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);


    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, RoleMapper roleMapper, RoleUtils roleUtils) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
        this.roleUtils = roleUtils;
    }

    @Override
    @Transactional
    public RoleDTO saveRole(RoleDTO roleDTO) {

        Optional<Role> actualRole = roleRepository.findByName(roleDTO.getRoleName());

        if (actualRole.isPresent()) { throw new IllegalArgumentException("Role already registered");}

        Role roleSaved = roleRepository.save(roleMapper.roleDTOToRole(roleDTO));
        RoleDTO savedRoleDTO = roleMapper.roleToRoleDTO(roleSaved);
        LOGGER.info("Role saved successfully: {}", savedRoleDTO);
        return savedRoleDTO;
    }

    @Override
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

    @Override
    @Transactional
    public RoleDTO update(RoleDTO roleDTO) {
        Role actualRole = roleUtils.findRoleByIdOrThrow(roleDTO.getRoleId());
        LOGGER.info("INFO actual role {}", roleDTO);

        actualRole.setName(roleDTO.getRoleName());

        Role updatedRole = roleRepository.save(actualRole);




        return roleMapper.roleToRoleDTO(updatedRole);

    }

    @Override
    public Long getCount(Long idRole) {
        Long countUsers = roleUtils.countUserByRole(idRole);
        LOGGER.info("For de Role with ID {} there are {} users", idRole, countUsers);
        return countUsers;
    }

    @Override
    public List<RoleDTO> getAll() {

        List<Role> roleList = roleUtils.getAllRoles();
        return roleList.stream()
                .map(roleMapper::roleToRoleDTO)
                .collect(Collectors.toList());
    }

}
