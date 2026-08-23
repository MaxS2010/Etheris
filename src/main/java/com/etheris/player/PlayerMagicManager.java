package com.etheris.player;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.etheris.player.PlayerMagicData;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerMagicManager {
    private static final Map<UUID, PlayerMagicData> PLAYER_MAGIC_DATA = new HashMap<>();
    private static final Set<UUID> ETHER_SPENT_THIS_TICK = new HashSet<>();
    private static final float REGEN_RATE_PER_TICK = 0.05f;

    public static PlayerMagicData createDefaultData() {
        return new PlayerMagicData(100f, 100f, 0f, 0f);
    }

    public static PlayerMagicData getOrCreate(UUID playerId) {
        return PLAYER_MAGIC_DATA.computeIfAbsent(playerId, ignored -> createDefaultData());
    }

    public static void saveToNbt(UUID playerId, Object nbt) {
        // no-op placeholder for compatibility with current runtime
    }

    public static void loadFromNbt(UUID playerId, Object nbt) {
        // no-op placeholder for compatibility with current runtime
    }

    public static void remove(UUID playerId) {
        PLAYER_MAGIC_DATA.remove(playerId);
    }

    public static void tickRegeneration(ServerPlayerEntity player) {
        PlayerMagicData data = getOrCreate(player.getUuid());

        if (ETHER_SPENT_THIS_TICK.remove(player.getUuid())) {
            return;
        }

        data.regenerateEther(REGEN_RATE_PER_TICK);

        if (data.getEther() >= 0f) {
            clearOverdraftEffects(player);
        }
    }

    public static void markEtherSpent(UUID playerId) {
        ETHER_SPENT_THIS_TICK.add(playerId);
    }

    private static void clearOverdraftEffects(ServerPlayerEntity player) {
        player.removeStatusEffect(StatusEffects.SLOWNESS);
        player.removeStatusEffect(StatusEffects.WEAKNESS);
        player.removeStatusEffect(StatusEffects.MINING_FATIGUE);
    }

    public static void registerEvents() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                tickRegeneration(player);
            }
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerMagicData data = getOrCreate(newPlayer.getUuid());
            data.setEther(0f);
        });

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            PlayerMagicData data = getOrCreate(newPlayer.getUuid());
            data.setEther(0f);
        });
    }
}

