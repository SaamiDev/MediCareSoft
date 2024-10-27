package com.microservice.user_management.Controller;

import com.microservice.user_management.DTOs.RegisterDTO;
import com.microservice.user_management.Service.AuthService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;


//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    AuthService authService;
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//
//    @PostMapping("/register")
//    public ResponseEntity<RegisterDTO> register(@RequestBody RegisterDTO registerDTO) {
//        logger.info("Received request to save User: {}", registerDTO);
//
//        try {
//            RegisterDTO newUser = authService.register(registerDTO);
//            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            logger.error(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//}
