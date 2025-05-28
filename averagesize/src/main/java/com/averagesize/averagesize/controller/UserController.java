package com.averagesize.averagesize.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averagesize.averagesize.dto.UserReqDTO;
import com.averagesize.averagesize.dto.UserResDTO;
import com.averagesize.averagesize.dto.UserUpdateDTO;
import com.averagesize.averagesize.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserResDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    // Create user
    @PostMapping
    public ResponseEntity<UserResDTO> createUser(@RequestBody UserReqDTO userReqDTO) {
        UserResDTO createdUser = userService.createUser(userReqDTO);
        return ResponseEntity.ok(createdUser);
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserResDTO> updateUser(@PathVariable UUID id,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Change user password
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id,
            @RequestBody String password,
            @RequestBody(required = true) String oldPassword) {
        userService.updatePassword(id, password, oldPassword);
        return ResponseEntity.noContent().build();
    }
}
