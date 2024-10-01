package com.microservice.user_management.Repositories;

import com.microservice.user_management.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    void deleteById(Long id);

    @Query("SELECT COUNT(*) FROM User WHERE role.id = :roleId")
    Long countUserByRole(@Param("roleId") Long roleId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.role.id = :defaultId WHERE u.role.id = :actualId ")
    void updateRoleToDefault(@Param("defaultId") Long defaultId, @Param("actualId") Long actualId);



}
