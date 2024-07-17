package com.hamza.student_management_system.user.facade;

import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;
import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.facade.interfaces.UserFacade;
import com.hamza.student_management_system.user.mapper.interfaces.UserMapper;
import com.hamza.student_management_system.user.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public UserDto findUserById(Long id) {
        return this.userMapper.mapUserToUserDto(this.userService.findUserById(id));
    }

    @Override
    public UserDto addNewUser(RegisterUserDto userDto) {
        User createdUser = this.userService.createUser(this.userMapper.mapRegisterUserDtoToUser(userDto));
        return this.userMapper.mapUserToUserDto(createdUser);
    }

    @Override
    public UserDto removeUser(Long userId) {
        User deletedUser = this.userService.deleteUser(userId);
        return this.userMapper.mapUserToUserDto(deletedUser);
    }
}
