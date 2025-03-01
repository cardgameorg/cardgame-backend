package com.cardgame.service;

import com.cardgame.entity.User;
import com.cardgame.repository.UserRepository;
import com.cardgame.response.AuthResponse;
import com.cardgame.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public void ThrowIfUsernameOccupied(String username) throws ResponseStatusException{
        if (userRepository.existsUserByUsername(username))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username occupied");
    }

    public AuthResponse edit(HttpServletRequest request) {
        return AuthResponse.of(UserUtils.currentUser());
    }


}
