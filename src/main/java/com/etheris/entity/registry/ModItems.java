package com.etheris.entity.registry;

import com.etheris.Etheris;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class ModItems {

    public static final RegistryKey<ItemGroup> ETHERIS_GROUP_KEY = RegistryKey.of(
        RegistryKeys.ITEM_GROUP,
        Etheris.id("etheris_group")
    );

    public static final Item TanzaniteOre = registerItem(
        "tanzanite_ore_ingot",
        new Item(new Item.Settings())
    );

    public static final Item FirstWand = registerItem(
        "magic_wand_one",
        new FirstWand(new Item.Settings().maxCount(1).maxDamage(250), "ether", 1)
    );

    public static final Item LightBulletItem = registerItem(
        "light_bullet_item",
        new Item(new Item.Settings())
    );
    
    public static final Item TanzaniteScythe = registerItem(
        "tanzanite_scythe",
        new Item(new Item.Settings())
    );
    public static final Item TanzaniteArrow = registerItem(
        "tanzanite_arrow",
        new Item(new Item.Settings())
    );
    public static final Item TanzaniteBow = registerItem(
        "tanzanite_rod",
        new Item(new Item.Settings())
    );
    public static final Item AgonyWand = registerItem(
        "file_0000000004c0820aa33c6b4005bca615",
        new Item(new Item.Settings())
    );

    public static final Item AnalyzeWand = registerItem(
        "analyze_wand",
        new AnalyzeWand(new Item.Settings().maxCount(1))
    );

    public static final Item BlueOre = registerItem(
        "blue_ore_ingot",
        new Item(new Item.Settings())
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(
            Registries.ITEM,
            Etheris.id(name),
            item
        );
    }

    public static void registerModItems() {
        Registry.register(Registries.ITEM_GROUP, ETHERIS_GROUP_KEY, FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + Etheris.MOD_ID + ".etheris_group"))
            .icon(() -> new ItemStack(FirstWand))
            .build()
        );

        ItemGroupEvents.modifyEntriesEvent(ETHERIS_GROUP_KEY).register(entries -> {
            entries.add(TanzaniteOre);
            entries.add(FirstWand);
            entries.add(LightBulletItem);
            entries.add(AnalyzeWand);
            entries.add(BlueOre);
        });
    }
}
