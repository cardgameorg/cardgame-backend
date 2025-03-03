package com.cardgame.repository;

import com.cardgame.entity.database.User;
import com.cardgame.response.UserResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsUserByUsername(@NotNull String username);

    @Transactional
    Optional<User> findUserByUsername(String username);

    List<User> findAllByUsernameIn(Collection<String> usernames);
}

