package com.etheris.block;

import com.etheris.Etheris;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import com.etheris.entity.registry.ModItems; // Твой правильный путь к предметам
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {

    public static final Block MAGIC_LOG = registerBlock("magic_log", 
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));

    public static final Block MAGIC_LEAVES = registerBlock("magic_leaves", 
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Etheris.id(name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Etheris.id(name), 
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.ETHERIS_GROUP_KEY).register(entries -> {
            entries.add(MAGIC_LOG);
            entries.add(MAGIC_LEAVES);
        });
    }
}
