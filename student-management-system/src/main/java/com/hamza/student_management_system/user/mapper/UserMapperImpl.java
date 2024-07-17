package com.hamza.student_management_system.user.mapper;

import com.hamza.student_management_system.core.utility.UserRoles;
import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;
import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.mapper.interfaces.UserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final BCryptPasswordEncoder encoder;

    @Override
    public User mapRegisterUserDtoToUser(RegisterUserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .passwordHash(encoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .role(UserRoles.Student.name())
                .build();
    }

    @Override
    public UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
