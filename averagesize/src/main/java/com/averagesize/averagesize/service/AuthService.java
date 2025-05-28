package com.averagesize.averagesize.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.averagesize.averagesize.dto.UserReqDTO;
import com.averagesize.averagesize.dto.UserResDTO;
import com.averagesize.averagesize.entity.User;
import com.averagesize.averagesize.mapper.UserMapper;
import com.averagesize.averagesize.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResDTO register(UserReqDTO userReqDTO) {
        if (userRepository.existsByEmail(userReqDTO.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = userMapper.toEntity(userReqDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public UserResDTO authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return userMapper.toDto(user);
    }

}
