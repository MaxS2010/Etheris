package com.etheris.playerclass;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class DruidHandler {
    private static final Set<Integer> provokedMobs = ConcurrentHashMap.newKeySet();

    private DruidHandler() {}

    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (!(entity instanceof ServerPlayerEntity player)) {
                return true;
            }
            PlayerClassData classData = PlayerClassManager.getOrCreate(player.getUuid());
            if (!classData.hasClass() || !"druid".equals(classData.getOwnedClass())) {
                return true;
            }

            if (source.getAttacker() instanceof HostileEntity attacker
                    && !provokedMobs.contains(attacker.getId())) {
                return false;
            }
            return true;
        });

        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (entity instanceof HostileEntity mob && source.getAttacker() instanceof PlayerEntity) {
                provokedMobs.add(mob.getId());
            }
        });
    }
}