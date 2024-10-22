package com.microservice.user_management.Service;

import com.microservice.user_management.DTOs.RoleDTO;
import java.util.List;



public interface RoleService {

    RoleDTO saveRole(RoleDTO roleDTO);

    void deleteRole(RoleDTO roleDTO);

    RoleDTO update(RoleDTO roleDTO);

    Long getCount(Long idRole);

    List<RoleDTO> getAll();

}
