package com.cardgame.repository;

import com.cardgame.entity.Pack;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

    @Query("SELECT p FROM Pack p WHERE p.publicId = :publicId ORDER BY p.version DESC LIMIT 1")
    Optional<Pack> findLatestByPublicId(@Param("publicId") String publicId);
}
