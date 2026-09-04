package com.etheris.playerclass;

import com.etheris.entity.registry.ModItems;
import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;

import java.util.List;

public final class ClassPurchaseHandler {
    private ClassPurchaseHandler() {}

    public static void handlePurchase(PlayerEntity player, String classId) {
        ClassDefinition def = ClassDefinition.byId(classId);
        if (def == null) {
            return;
        }

        PlayerClassData classData = PlayerClassManager.getOrCreate(player.getUuid());
        if (classData.hasClass()) {
            player.sendMessage(Text.literal("You already picked a class.").formatted(Formatting.RED), true);
            return;
        }

        PlayerMagicData magicData = PlayerMagicManager.getOrCreate(player.getUuid());
        if (magicData.getManipulation() < def.manipulationCost()) {
            player.sendMessage(Text.literal("Not enough manipulation.").formatted(Formatting.RED), true);
            return;
        }

        magicData.modifyManipulation(-def.manipulationCost());
        classData.setOwnedClass(classId);

        for (ItemStack reward : def.itemRewards()) {
            player.giveItemStack(reward.copy());
        }
        if (def.etherReward() > 0f) {
            magicData.modifyEther(def.etherReward());
        }
        if (def.extraHeartsSelf() > 0f) {
            addMaxHealth(player, def.extraHeartsSelf());
        }
        if (def.extraHeartsAllies() > 0f && player.getWorld() instanceof ServerWorld serverWorld) {
            List<PlayerEntity> nearby = serverWorld.getEntitiesByClass(PlayerEntity.class,
                    Box.from(player.getPos()).expand(20), p -> p != player);
            for (PlayerEntity ally : nearby) {
                addMaxHealth(ally, def.extraHeartsAllies());
            }
        }

        switch (classId) {
            case "mage" -> giveAllWands(player);
            case "warrior" -> {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.STRENGTH, StatusEffectInstance.INFINITE, 1, true, false, true));
                giveEnchantedItem(player, new ItemStack(Items.IRON_SWORD), Enchantments.SHARPNESS, 2);
            }
            case "archer" -> giveEnchantedItem(player, new ItemStack(Items.BOW), Enchantments.POWER, 2);
            case "healer" -> player.giveItemStack(new ItemStack(ModItems.HealingWand));
            case "lumberjack", "miner" -> player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.HASTE, StatusEffectInstance.INFINITE, 0, true, false, true));
            case "aquanaut" -> {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.WATER_BREATHING, StatusEffectInstance.INFINITE, 0, true, false, true));
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.DOLPHINS_GRACE, StatusEffectInstance.INFINITE, 0, true, false, true));
            }
            case "savage" -> player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.REGENERATION, StatusEffectInstance.INFINITE, 0, true, false, true));
            // "druid" — см. DruidHandler.java
        }

        player.sendMessage(Text.literal("You are now a " + def.name() + "!").formatted(Formatting.GREEN), false);
    }

    public static void clearClassEffects(PlayerEntity player) {
        player.removeStatusEffect(StatusEffects.STRENGTH);
        player.removeStatusEffect(StatusEffects.HASTE);
        player.removeStatusEffect(StatusEffects.WATER_BREATHING);
        player.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
        player.removeStatusEffect(StatusEffects.REGENERATION);

        var attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attributeInstance != null) {
            Identifier modifierId = Identifier.of("etheris", "class_extra_health");
            attributeInstance.removeModifier(modifierId);
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }

    private static void giveAllWands(PlayerEntity player) {
        player.giveItemStack(new ItemStack(ModItems.GrownStaff));
        player.giveItemStack(new ItemStack(ModItems.FirstWand));
        player.giveItemStack(new ItemStack(ModItems.AgonyWand));
        player.giveItemStack(new ItemStack(ModItems.HealingWand));
        player.giveItemStack(new ItemStack(ModItems.AnalyzeWand));
        player.giveItemStack(new ItemStack(ModItems.BuffWand));
    }

    private static void giveEnchantedItem(PlayerEntity player, ItemStack stack, RegistryKey<Enchantment> enchantmentKey, int level) {
        RegistryEntry<Enchantment> enchantment = player.getWorld()
                .getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(enchantmentKey)
                .orElseThrow();

        stack.addEnchantment(enchantment, level);
        player.giveItemStack(stack);
    }

    private static void addMaxHealth(PlayerEntity player, float extraHealth) {
        var attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attributeInstance == null) return;

        Identifier modifierId = Identifier.of("etheris", "class_extra_health");
        attributeInstance.removeModifier(modifierId);
        attributeInstance.addPersistentModifier(new EntityAttributeModifier(
                modifierId, extraHealth, EntityAttributeModifier.Operation.ADD_VALUE));
        player.setHealth(player.getHealth() + extraHealth);
    }
}