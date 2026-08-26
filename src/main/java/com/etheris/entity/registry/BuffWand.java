package com.etheris.entity.registry;

import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BuffWand extends Item {

    private static final float ETHER_COST = 50.0f;

    public BuffWand(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.pass(itemStack);
        }

        PlayerMagicData magicData = PlayerMagicManager.getOrCreate(user.getUuid());

        magicData.forceConsumeEther(ETHER_COST);

        int duration = 30 * 20; // 30 секунд

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, duration, 2));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, duration, 2));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, duration, 2));

        world.playSound(null, user.getX(), user.getY(), user.getZ(),
            SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 1.0F, 1.0F);

        itemStack.damage(1, user, PlayerEntity.getSlotForHand(hand));

        user.getItemCooldownManager().set(this, 45 * 20);

        return TypedActionResult.success(itemStack);
    }
}