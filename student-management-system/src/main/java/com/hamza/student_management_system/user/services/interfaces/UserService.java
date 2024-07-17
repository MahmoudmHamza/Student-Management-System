package com.hamza.student_management_system.user.services.interfaces;

import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.entities.User;

public interface UserService {
    User findUserById(Long id);
    User createUser(RegisterUserDto userDto);
    void deleteUser(Long id);
}
