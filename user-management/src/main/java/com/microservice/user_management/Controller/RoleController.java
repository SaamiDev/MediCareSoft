package com.microservice.user_management.Controller;

import com.microservice.user_management.DTOs.RoleDTO;
import com.microservice.user_management.Service.RoleService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @PostMapping("")
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO) {
        logger.info("Received request to save Role: {}", roleDTO);
        try {
            RoleDTO newRoleDTO = roleService.saveRole(roleDTO);

            return new ResponseEntity<>(newRoleDTO,HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            logger.error("ResponseStatusException occurred: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Error ocurred while saving Role: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getAll() {
        logger.info("Received request to get all Roles");
        try {
            List<RoleDTO> roleDTOList = roleService.getAll();
            if (roleDTOList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("Get all roles successfully {}", roleDTOList);
            return new ResponseEntity<>(roleDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching roles", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/{roleId}")
    public ResponseEntity<RoleDTO> deleteRole(@PathVariable String roleId) {
        logger.info("Received request to delete Role whith id: {}", roleId);
        try {
            roleService.deleteRole(roleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("ResponseStatusException ocurred: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }



    }

}
