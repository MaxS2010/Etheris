package com.etheris.entity.effect;

import com.etheris.Etheris;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public final class ModEffects {
    public static final RegistryEntry<StatusEffect> BLEEDING = Registry.registerReference(
            Registries.STATUS_EFFECT,
            Etheris.id("bleeding"),
            new BleedingStatusEffect()
    );

    private ModEffects() {
    }

    public static void registerEffects() {
        Etheris.LOGGER.info("Registering status effects for {}", Etheris.MOD_ID);
    }
}