package com.microservice.user_management.Repositories;


import com.microservice.user_management.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Long> {



}
