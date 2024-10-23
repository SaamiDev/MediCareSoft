package com.microservice.user_management.Service;

import com.microservice.user_management.DTOs.RoleDTO;
import java.util.List;
import java.util.Optional;


public interface RoleService {

    RoleDTO saveRole(RoleDTO roleDTO);

    void deleteRole(RoleDTO roleDTO);

    RoleDTO update(RoleDTO roleDTO);

    Optional<Long>getCount(Long idRole);

    List<RoleDTO> getAll();

}
