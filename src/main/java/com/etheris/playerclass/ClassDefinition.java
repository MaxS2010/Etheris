package com.etheris.playerclass;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public record ClassDefinition(
        String id,
        String name,
        String description,
        int manipulationCost,
        Item icon,
        ItemStack[] itemRewards,
        float etherReward,
        float extraHeartsSelf,
        float extraHeartsAllies
) {
    public static final ClassDefinition[] ALL = {
            new ClassDefinition("lumberjack", "Lumberjack",
                    "Chops wood 33% faster.\nGrants an iron axe and 50 ether.",
                    150, Items.IRON_AXE,
                    new ItemStack[]{new ItemStack(Items.IRON_AXE)}, 50f, 0f, 0f),

            new ClassDefinition("warrior", "Warrior",
                    "Permanent Strength II.\nGrants an iron sword with Sharpness II and 50 ether.",
                    255, Items.IRON_SWORD,
                    new ItemStack[]{}, 50f, 0f, 0f),

            new ClassDefinition("archer", "Archer",
                    "Grants a bow with Power II, a crossbow,\na stack of arrows, and 50 ether.",
                    185, Items.BOW,
                    new ItemStack[]{new ItemStack(Items.CROSSBOW), new ItemStack(Items.ARROW, 64)}, 50f, 0f, 0f),

            new ClassDefinition("mage", "Mage",
                    "Grants every wand, and 200 ether.",
                    500, Items.BLAZE_ROD,
                    new ItemStack[]{}, 200f, 0f, 0f),

            new ClassDefinition("healer", "Healer",
                    "Grants a Healing Wand, 5 extra hearts for yourself,\n3 extra hearts for allies, and 75 ether.",
                    350, Items.GOLDEN_APPLE,
                    new ItemStack[]{}, 75f, 10f, 6f),

            new ClassDefinition("miner", "Miner",
                    "Mines ore 33% faster.\nGrants an iron pickaxe and 50 ether.",
                    150, Items.IRON_PICKAXE,
                    new ItemStack[]{new ItemStack(Items.IRON_PICKAXE)}, 50f, 0f, 0f),

            new ClassDefinition("savage", "Savage",
                    "Cannot wear armor, but is immune to hunger and\npoison, and regenerates hearts faster.",
                    250, Items.LEATHER_CHESTPLATE,
                    new ItemStack[]{}, 0f, 0f, 0f),

            new ClassDefinition("aquanaut", "Aquanaut",
                    "Moves slower on land, but swims fast and\nbreathes underwater indefinitely. Grants 75 ether.",
                    185, Items.TURTLE_HELMET,
                    new ItemStack[]{}, 75f, 0f, 0f),

            new ClassDefinition("druid", "Druid",
                    "Hostile mobs won't attack you first unless\nprovoked. Grants 100 ether.",
                    300, Items.OAK_SAPLING,
                    new ItemStack[]{}, 100f, 0f, 0f)
    };

    public static ClassDefinition byId(String id) {
        for (ClassDefinition def : ALL) {
            if (def.id.equals(id)) {
                return def;
            }
        }
        return null;
    }
}