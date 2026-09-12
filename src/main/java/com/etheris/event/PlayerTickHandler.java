package com.etheris.event;

import com.etheris.playerclass.PlayerClassData;
import com.etheris.playerclass.PlayerClassManager;
import com.etheris.entity.registry.ModItems;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerTickHandler {
    private static final int CHECK_INTERVAL_TICKS = 20;
    private static int tickCount = 0;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCount++;
            if (tickCount < CHECK_INTERVAL_TICKS) {
                return;
            }
            tickCount = 0;

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                PlayerClassData classData = PlayerClassManager.getOrCreate(player.getUuid());
                if (classData.hasClass() && "savage".equals(classData.getOwnedClass())) {
                    applySavagePerks(player);
                }
                applyTanzaniteArmorEffects(player);
            }
        });
    }

    private static void applyTanzaniteArmorEffects(ServerPlayerEntity player) {
        updateArmorEffect(player, EquipmentSlot.HEAD, ModItems.TANZANITE_HELMET,
                StatusEffects.WATER_BREATHING);
        updateArmorEffect(player, EquipmentSlot.CHEST, ModItems.TANZANITE_CHESTPLATE,
                StatusEffects.NIGHT_VISION);
        updateArmorEffect(player, EquipmentSlot.LEGS, ModItems.TANZANITE_LEGGINGS,
                StatusEffects.SPEED);
        updateArmorEffect(player, EquipmentSlot.FEET, ModItems.TANZANITE_BOOTS,
                StatusEffects.SLOW_FALLING);
    }

    private static void updateArmorEffect(ServerPlayerEntity player, EquipmentSlot slot,
            net.minecraft.item.Item armorItem,
            net.minecraft.registry.entry.RegistryEntry<net.minecraft.entity.effect.StatusEffect> effect) {
        if (player.getEquippedStack(slot).isOf(armorItem)) {
            player.addStatusEffect(new StatusEffectInstance(effect, CHECK_INTERVAL_TICKS + 10, 0, true, false, true));
        } else {
            player.removeStatusEffect(effect);
        }
    }

    private static void applySavagePerks(ServerPlayerEntity player) {
        player.getHungerManager().setFoodLevel(20);
        player.getHungerManager().setSaturationLevel(20f);

        if (player.hasStatusEffect(StatusEffects.POISON)) {
            player.removeStatusEffect(StatusEffects.POISON);
        }

        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack equipped = player.getEquippedStack(slot);
            if (!equipped.isEmpty()) {
                player.equipStack(slot, ItemStack.EMPTY);
                if (!player.getInventory().insertStack(equipped)) {
                    player.dropItem(equipped, false);
                }
                player.sendMessage(Text.literal("Savages cannot wear armor!").formatted(Formatting.RED), true);
            }
        }
    }
}