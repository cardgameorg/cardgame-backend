package com.cardgame.service;

import com.cardgame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
