package com.cardgame.service;

import com.cardgame.entity.database.User;
import com.cardgame.entity.inMemory.Room;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    private ConcurrentHashMap<String, Room> roomMap;

    public Room createGame(User user) {
        Room initialRoom = new Room(user);
        roomMap.put(initialRoom.getId(),initialRoom);
        return initialRoom;
    }


}
