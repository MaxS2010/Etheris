package com.etheris.entity.mobs;

import com.etheris.Etheris;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<EtherialCrystalEntity> ETHEREAL_CRYSTAL =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(Etheris.MOD_ID, "ethereal_crystal"),
                    EntityType.Builder
                            .<EtherialCrystalEntity>create(EtherialCrystalEntity::new, SpawnGroup.CREATURE)
                            .dimensions(0.8f, 1.6f)
                            .maxTrackingRange(64)
                            .trackingTickInterval(3)
                            .build()
            );

    public static final EntityType<EtherialWraithEntity> ETHEREAL_WRAITH =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(Etheris.MOD_ID, "ethereal_wraith"),
                    EntityType.Builder
                            .<EtherialWraithEntity>create(EtherialWraithEntity::new, SpawnGroup.MONSTER)
                            .dimensions(0.6f, 1.8f)
                            .maxTrackingRange(64)
                            .trackingTickInterval(3)
                            .build()
            );

    public static void registerEntities() {
        Etheris.LOGGER.info("Registering entities for {}", Etheris.MOD_ID);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ETHEREAL_WRAITH, EtherialWraithEntity.createEtherialWraithAttributes());
    }
}
