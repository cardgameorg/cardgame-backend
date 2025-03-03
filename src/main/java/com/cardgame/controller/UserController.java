package com.cardgame.controller;

import com.cardgame.filters.CustomWebSocketHandler;
import com.cardgame.repository.UserRepository;
import com.cardgame.response.AuthResponse;
import com.cardgame.response.UserResponse;
import com.cardgame.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final CustomWebSocketHandler webSocketHandler;
    private final UserRepository userRepository;

    @PostMapping("/edit")
    public ResponseEntity<AuthResponse> editProfile(HttpServletRequest request) {
        return ResponseEntity.ok(userService.edit(request));
    }


}
