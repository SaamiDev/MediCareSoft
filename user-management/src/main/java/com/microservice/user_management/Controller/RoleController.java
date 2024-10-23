package com.microservice.user_management.Controller;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Service.RoleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> saveRole(@Valid @RequestBody RoleDTO roleDTO) {
        LOGGER.info("Received request to save role: {}", roleDTO.getRoleName());
        RoleDTO newRoleDTO = roleService.saveRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoleDTO);
    }

    @GetMapping("/count/{roleId}")
    public ResponseEntity<Long> getCount(@PathVariable Long roleId) {
        LOGGER.info("Received request to get count of users for role ID: {}", roleId);
        Long countUsers = roleService.getCount(roleId);
        return ResponseEntity.ok(countUsers);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getAll() {
        LOGGER.info("Received request to get all roles");
        List<RoleDTO> roleDTOList = roleService.getAll();
        return ResponseEntity.ok(roleDTOList);
    }

    @PutMapping
    public ResponseEntity<RoleDTO> updateRole(@Valid @RequestBody RoleDTO roleDTO) {
        LOGGER.info("Received request to update role with ID: {}", roleDTO.getRoleId());
        RoleDTO updatedRoleDTO = roleService.update(roleDTO);
        return ResponseEntity.ok(updatedRoleDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRole(@Valid @RequestBody RoleDTO roleDTO) {
        LOGGER.info("Received request to delete role: {}", roleDTO);
        roleService.deleteRole(roleDTO);
        return ResponseEntity.ok().build();
    }
}

