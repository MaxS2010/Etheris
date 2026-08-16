package com.etheris.entity.mobs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EtherialCrystalEntity extends PassiveEntity {

    public EtherialCrystalEntity(EntityType<? extends EtherialCrystalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 1.0d));

        this.targetSelector.add(1, new RevengeGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity other) {
        return ModEntities.ETHEREAL_CRYSTAL.create(world);
    }
}
