package com.microservice.user_management.Service;


import com.microservice.user_management.DTOs.UserDTO;
import com.microservice.user_management.Entities.User;
import com.microservice.user_management.Mappers.UserMapper;
import com.microservice.user_management.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;


    public UserDTO saveUser(UserDTO userDTO) {
        User userSave = userRepository.save(userMapper.userDTOToUser(userDTO));
        return userMapper.userToUserDTO(userSave);
    }


}
