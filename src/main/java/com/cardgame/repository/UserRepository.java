package com.cardgame.repository;

import com.cardgame.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsUserByUsername(@NotNull String username);

    Optional<User> findUserByUsername(String username);
}
