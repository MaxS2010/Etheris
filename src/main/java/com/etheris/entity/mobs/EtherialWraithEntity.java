package com.etheris.entity.mobs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EtherialWraithEntity extends HostileEntity {

    public EtherialWraithEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0d, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0d));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createEtherialWraithAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0d)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25d)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0d)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0d)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.1d);
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.getWorld().isClient) {
            this.spawnParticles();
        }
    }

    private void spawnParticles() {
        
    }

    @Override
    public int getLimitPerChunk() {
        return 8;
    }
}
