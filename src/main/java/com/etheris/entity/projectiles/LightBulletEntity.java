package com.etheris.entity.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class LightBulletEntity extends ThrownItemEntity {

    private float damage = 4.0F;

    public LightBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public LightBulletEntity(World world, LivingEntity owner) {
        super(ModProjectiles.LIGHT_BULLET, owner, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age > 100) {
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);

        if (!getWorld().isClient()) {
            if (hitResult.getEntity() instanceof LivingEntity entity) {
                entity.damage(getDamageSources().magic(), damage);
            }

            discard();
        }
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!getWorld().isClient()) {
            discard();
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    @Override
    protected Item getDefaultItem() {
        return com.etheris.entity.registry.ModItems.LightBulletItem;
    }

    @Override
    public boolean shouldSave() {
        return false;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Damage")) {
            damage = nbt.getFloat("Damage");
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("Damage", damage);
    }

    @Override
    protected double getGravity() {
        return 0.0D;
    }
}