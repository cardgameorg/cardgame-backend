package com.cardgame.controller;

import com.cardgame.request.CreateUserDto;
import com.cardgame.request.LoginDto;
import com.cardgame.response.AuthResponse;
import com.cardgame.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody CreateUserDto dto,
                                                 HttpServletResponse response) {
        return new ResponseEntity<>(authenticationService.register(dto, response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto dto,
                                              HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.login(dto, response));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate() {
        return ResponseEntity.ok(authenticationService.authenticate());
    }
}
