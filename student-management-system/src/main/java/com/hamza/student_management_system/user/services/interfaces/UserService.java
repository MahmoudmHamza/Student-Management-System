package com.hamza.student_management_system.user.services.interfaces;

import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.entities.User;

public interface UserService {
    User findUserById(Long id);
    User findUserByName(String username);
    User createUser(User user);
    User deleteUser(Long id);
}
