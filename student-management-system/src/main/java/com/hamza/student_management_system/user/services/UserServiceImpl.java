package com.hamza.student_management_system.user.services;

import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.repositories.UserRepository;
import com.hamza.student_management_system.user.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user with id: " +  id));
    }

    @Override
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User deleteUser(Long id) {
        User deletedUser = this.findUserById(id);
        userRepository.deleteById(id);

        return deletedUser;
    }
}
