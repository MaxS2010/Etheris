package com.etheris;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etheris.block.ModBlocks;
import com.etheris.block.ModNatureRegistry;
import com.etheris.client.ui.ClassesKeyHandler;
import com.etheris.client.ui.UiKeyHandler;
import com.etheris.command.MagicCommand;
import com.etheris.entity.mobs.ModEntities;
import com.etheris.entity.projectiles.ModProjectiles;
import com.etheris.entity.effect.ModEffects;
import com.etheris.entity.registry.ModItems;
import com.etheris.event.PlayerTickHandler;
import com.etheris.network.ModNetworking;
import com.etheris.player.PlayerMagicManager;
import com.etheris.playerclass.DruidHandler;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Etheris implements ModInitializer {
    public static final String MOD_ID = "etheris";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModNetworking.registerPayloads();
        ModEffects.registerEffects();
        MagicCommand.initializeCommand();
        ModItems.registerModItems();
        UiKeyHandler.register();
        ClassesKeyHandler.register();
        ModBlocks.registerModBlocks();

        ModNatureRegistry.registerNatureValues(ModBlocks.MAGIC_LOG, 25.0f, 0.85f);
        ModNatureRegistry.registerNatureValues(ModBlocks.MAGIC_LEAVES, 10.0f, 0.95f);

        PlayerMagicManager.registerEvents();
        ModProjectiles.registerProjectiles();
        ModEntities.registerEntities();
        ModEntities.registerAttributes();
        PlayerTickHandler.register();
        DruidHandler.register();

        LOGGER.info("Hello Fabric world!");
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}