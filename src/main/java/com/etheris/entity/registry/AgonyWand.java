package com.etheris.entity.registry;

import com.etheris.entity.projectiles.LightBulletEntity;
import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;
import com.etheris.network.ModNetworking;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AgonyWand extends Item {
    private final String material;
    private final int powerLevel;

    public AgonyWand(Settings settings, String material, int powerLevel) {
        super(settings);
        this.material = material;
        this.powerLevel = powerLevel;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            PlayerMagicData magicData = PlayerMagicManager.getOrCreate(user.getUuid());
            
            boolean bleedingSpell = ModNetworking.getSelectedSpell(user.getUuid()) == 1;
            float cost = bleedingSpell ? 45f : 60f;
            float remainingEther = magicData.forceConsumeEther(cost);

            LightBulletEntity bullet = new LightBulletEntity(world, user);
            
            // Базова логіка пошкодження та швидкості з вашого коду
            bullet.setDamage(bleedingSpell ? 3.0F + powerLevel : 4.0F + powerLevel * 1.5F);
            float speed = 2.0F + powerLevel * 0.5F;
            bullet.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, speed, 1.0F);
            bullet.velocityDirty = true;
            bullet.velocityModified = true;

            if (bleedingSpell) {
                bullet.setBleeding(true, powerLevel);
            } else {
                bullet.setDarkEffects(true, powerLevel);
            }

            world.spawnEntity(bullet);
            stack.damage(1, user, EquipmentSlot.MAINHAND);

            // Логіка овердрафту (залишаємо вашу базову систему)
            if (remainingEther < 0f) {
                applyOverdraftEffects(world, user, remainingEther);
                if (remainingEther <= -300f) {
                    user.damage(world.getDamageSources().magic(), Float.MAX_VALUE);
                }
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(stack, world.isClient());
    }

    private void applyOverdraftEffects(World world, PlayerEntity user, float remainingEther) {
        int negativeLevel = (int)Math.min(4, Math.ceil(Math.abs(remainingEther) / 10f));
        int duration = 100 + negativeLevel * 100;

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, duration, negativeLevel, false, false, true));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, duration, negativeLevel, false, false, true));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, duration, Math.max(0, negativeLevel - 1), false, false, true));

        if (remainingEther <= -200f && remainingEther > -300f) {
            float damage = Math.max(1f, user.getMaxHealth() / 2f);
            user.damage(world.getDamageSources().magic(), damage);
        }
    }
}
