package com.etheris;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.etheris.entity.registry.ModItems;
import com.etheris.block.ModBlocks;
import com.etheris.block.ModNatureRegistry;
import com.etheris.entity.projectiles.ModProjectiles;
import com.etheris.command.MagicCommand;
import com.etheris.player.PlayerMagicManager;

public class Etheris implements ModInitializer {
	public static final String MOD_ID = "etheris";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MagicCommand.initializeCommand();
		ModItems.registerModItems();

		ModBlocks.registerModBlocks();

		ModNatureRegistry.registerNatureValues(ModBlocks.MAGIC_LOG, 25.0f, 0.85f);
		ModNatureRegistry.registerNatureValues(ModBlocks.MAGIC_LEAVES, 10.0f, 0.95f);

		PlayerMagicManager.registerEvents();
		ModProjectiles.registerProjectiles();

		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
