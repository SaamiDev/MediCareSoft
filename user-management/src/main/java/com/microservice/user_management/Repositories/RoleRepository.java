package com.microservice.user_management.Repositories;

import com.microservice.user_management.Entities.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String Name);

    @Query("SELECT r FROM Role r where r.roleId = :roleId")
    Optional<Role> findById(@Param("roleId") String roleId);



    @Modifying
    @Transactional
    @Query("DELETE FROM Role r WHERE r.roleId = :roleId")
    void deleteByRoleId(@Param("roleId") String roleId);



}
