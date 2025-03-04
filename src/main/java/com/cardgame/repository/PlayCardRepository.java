package com.cardgame.repository;

import com.cardgame.entity.database.PlayCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayCardRepository extends JpaRepository<PlayCard,Long> {
}
