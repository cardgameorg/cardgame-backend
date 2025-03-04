package com.cardgame.service;

import com.cardgame.entity.database.Pack;
import com.cardgame.entity.database.User;
import com.cardgame.repository.PackRepository;
import com.cardgame.repository.PlayCardRepository;
import com.cardgame.repository.PromptCardRepository;
import com.cardgame.request.CreatePackDto;
import com.cardgame.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CardService {
    private final PlayCardRepository playCardRepository;
    private final PromptCardRepository promptCardRepository;
    private final PackRepository packRepository;

    public Pack createPack(CreatePackDto createPackDto) {
        User user = UserUtils.getCurrentUser();
        return null;
    }

}
