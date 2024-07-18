package com.hamza.student_management_system.user.controllers;

import com.hamza.student_management_system.user.datamodels.UserDto;
import com.hamza.student_management_system.user.facade.interfaces.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
        return ResponseEntity.ok(userFacade.findUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> removeUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userFacade.removeUser(userId));
    }
}
