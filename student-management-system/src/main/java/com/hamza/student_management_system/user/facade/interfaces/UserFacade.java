package com.hamza.student_management_system.user.facade.interfaces;

import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;

import java.util.List;

public interface UserFacade {
    UserDto findUserById(Long id);
    UserDto addNewUser(RegisterUserDto userDto);
    UserDto removeUser(Long id);
}
