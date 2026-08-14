package com.etheris.block;

import com.etheris.Etheris;
import com.etheris.entity.registry.ModItems;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final Block MAGIC_LOG = registerBlock(
            "magic_log",
            new EtherPillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG), 50.0f, 1.0f)
    );

    public static final Block MAGIC_LEAVES = registerBlock(
            "magic_leaves",
            new EtherBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).nonOpaque(), 75.0f, 1.0f)
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
            // entries.add(ETHER_BLOCK);
        });
    }

    public static class EtherBlock extends Block {
        private final float ether;
        private final float purity;

        public EtherBlock(Settings settings, float ether, float purity) {
            super(settings);
            this.ether = ether;
            this.purity = purity;
        }

        public float getEther() {
            return ether;
        }

        public float getPurity() {
            return purity;
        }
    }

    public static class EtherPillarBlock extends PillarBlock {
        private final float ether;
        private final float purity;

        public EtherPillarBlock(Settings settings, float ether, float purity) {
            super(settings);
            this.ether = ether;
            this.purity = purity;
        }

        public float getEther() {
            return ether;
        }

        public float getPurity() {
            return purity;
        }
    }
}