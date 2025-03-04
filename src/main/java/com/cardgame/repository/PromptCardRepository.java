package com.cardgame.repository;

import com.cardgame.entity.database.PromptCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptCardRepository extends JpaRepository<PromptCard,Long> {
}
