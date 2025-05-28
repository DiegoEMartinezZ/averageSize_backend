package com.averagesize.averagesize.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averagesize.averagesize.dto.LoginReqDTO;
import com.averagesize.averagesize.dto.UserReqDTO;
import com.averagesize.averagesize.dto.UserResDTO;
import com.averagesize.averagesize.service.AuthService;
import com.averagesize.averagesize.service.SessionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserReqDTO userReqDTO) {
        try {
            UserResDTO registeredUser = authService.register(userReqDTO);
            String token = sessionService.createSession(registeredUser);

            Map<String, Object> res = new HashMap<>();
            res.put("user", registeredUser);
            res.put("token", token);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO loginReqDTO) {
        try {
            UserResDTO user = authService.authenticate(loginReqDTO.getEmail(), loginReqDTO.getPassword());
            String token = sessionService.createSession(user);

            Map<String, Object> res = new HashMap<>();
            res.put("user", user);
            res.put("token", token);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

}
