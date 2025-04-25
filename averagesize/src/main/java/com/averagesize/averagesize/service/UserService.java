package com.averagesize.averagesize.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.averagesize.averagesize.dto.UserReqDTO;
import com.averagesize.averagesize.dto.UserResDTO;
import com.averagesize.averagesize.dto.UserUpdateDTO;
import com.averagesize.averagesize.entity.User;
import com.averagesize.averagesize.exceptions.ResourceNotFoundException;
import com.averagesize.averagesize.exceptions.ServiceException;
import com.averagesize.averagesize.mapper.UserMapper;
import com.averagesize.averagesize.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // List Users
    @Transactional(readOnly = true)
    public List<UserResDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    // Find user by ID
    @Transactional(readOnly = true)
    public UserResDTO findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Doesn't Exist"));
        return userMapper.toDto(user);
    }

    // Find User by Email
    @Transactional(readOnly = true)
    public UserResDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Doesn't Exist: " + email));
        return userMapper.toDto(user);
    }

    // Create User
    @Transactional
    public UserResDTO createUser(UserReqDTO userDTO) {
        validateUserRequest(userDTO);

        // Create and configure user entity
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreateAt(LocalDateTime.now());
        user.setActive(true);

        try {
            User savedUser = userRepository.save(user);
            return userMapper.toDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("Could not create user due to data integrity violation", e);
        } catch (Exception e) {
            throw new ServiceException("Failed to create user", e);
        }
    }

    // Update a User
    @Transactional
    public UserResDTO updateUser(UUID id, UserUpdateDTO updateUserDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found in our DB"));

        // Update fields if they are provided
        if (StringUtils.hasText(updateUserDTO.getName())) {
            existingUser.setName(updateUserDTO.getName());
        }
        if (StringUtils.hasText(updateUserDTO.getLastName())) {
            existingUser.setLastName(updateUserDTO.getLastName());
        }
        if (StringUtils.hasText(updateUserDTO.getEmail())) {
            if (!isValidEmail(updateUserDTO.getEmail())) {
                throw new IllegalArgumentException("Email is not valid");
            }
            if (!existingUser.getEmail().equals(updateUserDTO.getEmail()) &&
                    userRepository.existsByEmail(updateUserDTO.getEmail())) {
                throw new IllegalArgumentException("Email already exists in our DB");
            }
            existingUser.setEmail(updateUserDTO.getEmail());
        }
        // Update password if provided
        if (StringUtils.hasText(updateUserDTO.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }

        // Update beta tester status if needed
        existingUser.setBetaTester(updateUserDTO.isBetaTester());

        try {
            User updatedUser = userRepository.save(existingUser);
            return userMapper.toDto(updatedUser);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("Could not update user due to data integrity violation", e);
        } catch (Exception e) {
            throw new ServiceException("Failed to update user", e);
        }
    }

    // Delete User
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found by ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // Activate/Deactivate User
    @Transactional
    public UserResDTO setUserActive(UUID id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by ID: " + id));
        user.setActive(active);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    // Email validation
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    // Validate user request DTO
    private void validateUserRequest(UserReqDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }
        if (!StringUtils.hasText(userDTO.getName())) {
            throw new IllegalArgumentException("Name is required");
        }
        if (!StringUtils.hasText(userDTO.getLastName())) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (!StringUtils.hasText(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!isValidEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email is not valid");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists in our DB");
        }
        if (!StringUtils.hasText(userDTO.getPassword())) {
            throw new IllegalArgumentException("Password is required");
        }
    }
}