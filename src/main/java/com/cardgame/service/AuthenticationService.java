package com.cardgame.service;

import com.cardgame.entity.User;
import com.cardgame.repository.UserRepository;
import com.cardgame.request.CreateUserDto;
import com.cardgame.request.LoginDto;
import com.cardgame.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthResponse register(CreateUserDto dto) {
        userService.ThrowIfUsernameOccupied(dto.getUsername());

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        user = userRepository.save(user);

        return AuthResponse.of(user);

    }

    public AuthResponse login(LoginDto dto) {
        Optional<User> optionalUser = userRepository.findUserByUsername(dto.getUsername());
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(dto.getPassword(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials");
        }

        return AuthResponse.of(user);

    }
}
