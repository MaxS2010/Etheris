package com.etheris.entity.registry;

import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;
import com.etheris.network.ModNetworking;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HealingWand extends Item {

    private static final float ETHER_COST = 20.0f;
    private static final float HEAL_AMOUNT = 6.0f; 

    public HealingWand(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.pass(stack);
        }

        if (ModNetworking.getSelectedSpell(user.getUuid()) == 1) {
            castRegeneration(world, user, hand);
            return TypedActionResult.success(stack);
        }

        PlayerMagicData data = PlayerMagicManager.getOrCreate(user.getUuid());
        data.forceConsumeEther(ETHER_COST);

        float newHealth = Math.min(user.getHealth() + HEAL_AMOUNT, user.getMaxHealth());
        user.setHealth(newHealth);

        user.sendMessage(Text.literal("+3 hearts healed").formatted(Formatting.GREEN), true);

        return TypedActionResult.success(stack);
    }

    public void castRegeneration(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            return;
        }

        PlayerMagicData data = PlayerMagicManager.getOrCreate(user.getUuid());
        float etherCost = 30.0f;
        data.forceConsumeEther(etherCost);

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20 * 10, 0));
        user.getStackInHand(hand).damage(1, user, PlayerEntity.getSlotForHand(hand));
        user.getItemCooldownManager().set(this, 20 * 10);
    }
}