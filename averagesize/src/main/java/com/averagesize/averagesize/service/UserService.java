package com.averagesize.averagesize.service;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.averagesize.averagesize.entity.User;
import com.averagesize.averagesize.exceptions.ResourceNotFoundException;
import com.averagesize.averagesize.exceptions.ServiceException;
import com.averagesize.averagesize.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // List Users
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Find user by ID
    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Doesn't Exist"));
    }

    // Find Cliend by Email
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Doesn't Exist: " + email));
    }

    // Create User
    @Transactional
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        // hasText(): Check for whitespaces-only strings
        if (!StringUtils.hasText(user.getName())) {
            throw new IllegalArgumentException("Name is required");
        }
        if (!StringUtils.hasText(user.getLastName())) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is not valid");
        }
        // existsByEmail need to define in the repository interface of user
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists in our DB");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new IllegalArgumentException("Password is required");
        }

        String hashesPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashesPassword);

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("Could not create user due to data integrity violation", e);
        } catch (Exception e) {
            throw new ServiceException("Failed to create user", e);
        }

    }

    // Update a Client
    @Transactional
    public User updateUser(UUID id, User updateUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found in our DB"));
        if (StringUtils.hasText(updateUser.getName())) {
            existingUser.setName(updateUser.getName());
        }
        if (StringUtils.hasText(updateUser.getLastName())) {
            existingUser.setLastName(updateUser.getLastName());
        }
        if (StringUtils.hasText(updateUser.getEmail())) {
            if (!isValidEmail(updateUser.getEmail())) {
                throw new IllegalArgumentException("Email is not valid");
            }
            if (!existingUser.getEmail().equals(updateUser.getEmail()) &&
                    userRepository.existsByEmail(updateUser.getEmail())) {
                throw new IllegalArgumentException("Email already exists in our DB");
            }
            existingUser.setEmail(updateUser.getEmail());
        }
        if (StringUtils.hasText(updateUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }

        try {
            return userRepository.save(existingUser);
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

    // Email validation
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

}
