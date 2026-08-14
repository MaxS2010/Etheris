
package com.etheris.block;

import com.etheris.Etheris;
import com.etheris.entity.registry.ModItems;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    // TODO (Андрій): Задати ля блоків ванільних рослин значення ефіру і чистоти. Почати з убового дерева і листви. 
    public static final Block MAGIC_LOG = registerBlock(
            "magic_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG))
    );

    public static final Block MAGIC_LEAVES = registerBlock("magic_leaves", 
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).nonOpaque()));

    public static final Block ETHER_BLOCK = registerBlock("ether_block",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.LAPIS_BLUE)
                    .strength(3.0f, 6.0f)
                    .requiresTool()
                    .luminance(state -> 8)
                    .sounds(net.minecraft.sound.BlockSoundGroup.AMETHYST_BLOCK)
            )
    );

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
            entries.add(ETHER_BLOCK);
        });
    }
}
