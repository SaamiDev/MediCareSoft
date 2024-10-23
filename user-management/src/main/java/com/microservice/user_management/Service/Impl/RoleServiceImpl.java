package com.microservice.user_management.Service.Impl;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Entities.Role;
import com.microservice.user_management.Exceptions.RoleExceptions.RoleAlreadyExistException;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
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

        roleRepository.findByName(roleDTO.getRoleName())
                .ifPresent(role -> {
                    LOGGER.warn("Attempt to register a duplicate role: {}", roleDTO.getRoleName());
                    throw new RoleAlreadyExistException(roleDTO.getRoleName());
                });

        Role role = roleMapper.roleDTOToRole(roleDTO);
        Role roleSaved = roleRepository.save(role);
        RoleDTO roleSavedDTO = roleMapper.roleToRoleDTO(roleSaved);

        LOGGER.info("Role saved sucessfully: {}", roleSavedDTO);

        return roleSavedDTO;
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

        roleRepository.delete(actualRole);

    }

    @Override
    @Transactional
    public RoleDTO update(RoleDTO roleDTO) {
        Role actualRole = roleUtils.findRoleByIdOrThrow(roleDTO.getRoleId());

        LOGGER.info("Updating role with ID: {}, Current role data: {}", roleDTO.getRoleId(), roleDTO);

        // Verificar si el nuevo nombre ya está en uso por otro rol
        roleRepository.findByName(roleDTO.getRoleName())
                        .ifPresent(role -> {
                            // Si el rol encontrado tiene un ID distinto, significa que otro rol ya tiene ese nombre
                            if (!role.getRoleId().equals(roleDTO.getRoleId())) {
                                throw new RoleAlreadyExistException(roleDTO.getRoleName());
                            }
                        });


        actualRole.setName(roleDTO.getRoleName());
        Role updatedRole = roleRepository.save(actualRole);
        LOGGER.info("Role updated succesfully: New role data: {}", roleDTO);

        return roleMapper.roleToRoleDTO(updatedRole);

    }

    @Override
    public Optional<Long> getCount(Long idRole) {
        Long countUsers = roleUtils.countUserByRole(idRole);

        if (countUsers == null || countUsers == 0) {
            LOGGER.warn("No users found for Role with ID {}", idRole);
            return Optional.empty();
        }

        LOGGER.info("For de Role with ID {} there are {} users", idRole, countUsers);
        return Optional.of(countUsers);
    }

    @Override
    public List<RoleDTO> getAll() {

        List<Role> roleList = roleUtils.getAllRoles();
        if (roleList.isEmpty()) { LOGGER.warn("No roles found in the database"); }

        return roleList.stream()
                .map(roleMapper::roleToRoleDTO)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

}
