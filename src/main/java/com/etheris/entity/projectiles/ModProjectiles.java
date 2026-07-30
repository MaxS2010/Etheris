package com.etheris.entity.projectiles;

import com.etheris.Etheris;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModProjectiles {

    public static final EntityType<LightBulletEntity> LIGHT_BULLET =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(Etheris.MOD_ID, "light_bullet"),
                    EntityType.Builder
                            .<LightBulletEntity>create(LightBulletEntity::new, SpawnGroup.MISC)
                            .dimensions(0.2F, 0.2F)
                            .maxTrackingRange(64)
                            .trackingTickInterval(1)
                            .build()
            );

    public static void registerProjectiles() {
        Etheris.LOGGER.info("Registering projectiles for {}", Etheris.MOD_ID);
    }
}