package com.etheris.network;

import com.etheris.Etheris;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ModNetworking {
    private static final Map<UUID, Integer> SELECTED_SPELLS = new ConcurrentHashMap<>();

    public record SelectSpellPayload(int slot) implements CustomPayload {
        public static final CustomPayload.Id<SelectSpellPayload> ID =
                new CustomPayload.Id<>(Etheris.id("select_spell"));
        public static final PacketCodec<RegistryByteBuf, SelectSpellPayload> CODEC =
                PacketCodec.tuple(PacketCodecs.INTEGER, SelectSpellPayload::slot, SelectSpellPayload::new);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    private ModNetworking() {
    }

    public static void registerPayloads() {
        PayloadTypeRegistry.playC2S().register(SelectSpellPayload.ID, SelectSpellPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SelectSpellPayload.ID, (payload, context) -> {
            int slot = Math.max(0, Math.min(3, payload.slot()));
            SELECTED_SPELLS.put(context.player().getUuid(), slot);
        });
    }

    public static int getSelectedSpell(UUID playerId) {
        return SELECTED_SPELLS.getOrDefault(playerId, 0);
    }
}