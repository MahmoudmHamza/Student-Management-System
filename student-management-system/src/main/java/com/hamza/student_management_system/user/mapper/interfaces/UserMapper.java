package com.hamza.student_management_system.user.mapper.interfaces;

import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;
import com.hamza.student_management_system.user.entities.User;

public interface UserMapper {

    User mapRegisterUserDtoToUser(RegisterUserDto userDto);
    UserDto mapUserToUserDto(User user);
}
