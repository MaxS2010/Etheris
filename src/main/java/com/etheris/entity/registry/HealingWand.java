package com.etheris.entity.registry;

import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;

import net.minecraft.entity.player.PlayerEntity;
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

        PlayerMagicData data = PlayerMagicManager.getOrCreate(user.getUuid());

        if (!data.consumeEther(ETHER_COST)) {
            user.sendMessage(Text.literal("Not enough ether!").formatted(Formatting.RED), true);
            return TypedActionResult.fail(stack);
        }

        float newHealth = Math.min(user.getHealth() + HEAL_AMOUNT, user.getMaxHealth());
        user.setHealth(newHealth);

        user.sendMessage(Text.literal("+3 hearts healed").formatted(Formatting.GREEN), true);

        return TypedActionResult.success(stack);
    }
}