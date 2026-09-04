package com.etheris.playerclass;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerClassManager {
    private static final Map<UUID, PlayerClassData> DATA = new ConcurrentHashMap<>();

    private PlayerClassManager() {}

    public static PlayerClassData getOrCreate(UUID playerId) {
        return DATA.computeIfAbsent(playerId, id -> new PlayerClassData());
    }
}