package com.cardgame.service;

import com.cardgame.entity.User;
import com.cardgame.repository.UserRepository;
import com.cardgame.request.CreateUserDto;
import com.cardgame.request.LoginDto;
import com.cardgame.response.AuthResponse;
import com.cardgame.utils.UserUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    public Cookie tokenCookie(User user) {
        var token = JwtService.generateToken(user);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(jwtService.getExpirationSeconds(token));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    public Cookie refreshTokenCookie(User user) {
        var refreshToken = JwtService.generateRefreshToken(user);
        Cookie cookie = new Cookie("refreshtoken", refreshToken);
        cookie.setMaxAge(jwtService.getExpirationSeconds(refreshToken));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }



    public AuthResponse register(CreateUserDto dto, HttpServletResponse response) {
        userService.ThrowIfUsernameOccupied(dto.getUsername());

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        user = userRepository.save(user);

        response.addCookie(tokenCookie(user));
        response.addCookie(refreshTokenCookie(user));
        return AuthResponse.of(user);
    }

    public AuthResponse login(LoginDto dto, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findUserByUsername(dto.getUsername());

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(dto.getPassword(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials");
        }

        response.addCookie(tokenCookie(user));
        response.addCookie(refreshTokenCookie(user));
        return AuthResponse.of(user);
    }

    public AuthResponse authenticate() {
        User user = UserUtils.currentUser();
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"please log in");
        return AuthResponse.of(user);
    }

}
