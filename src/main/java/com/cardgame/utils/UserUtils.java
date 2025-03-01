package com.cardgame.utils;

import com.cardgame.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    public static User currentUser () {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
