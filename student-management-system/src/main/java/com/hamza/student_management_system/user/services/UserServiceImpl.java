package com.hamza.student_management_system.user.services;

import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.repositories.UserRepository;
import com.hamza.student_management_system.user.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "userDetails", key = "#id")
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find user with id: %d", id)));
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with name %s is not found", username)));
    }

    @Override
    public User createUser(User newUser) {
        log.info("Creating new user with name {} {}", newUser.getFirstName(), newUser.getLastName());
        return userRepository.save(newUser);
    }

    @Override
    @CacheEvict(value = "userDetails", key = "#id")
    public User deleteUser(Long id) {
        log.info(String.format("Delete user with id: %d", id));
        User deletedUser = this.findUserById(id);
        userRepository.deleteById(id);

        return deletedUser;
    }
}
