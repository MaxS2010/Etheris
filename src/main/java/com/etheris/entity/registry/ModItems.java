package com.etheris.entity.registry;

import com.etheris.Etheris;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorItem;
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

    public static final Item TanzaniteScrap = registerItem(
        "tanzanite_scrap",
        new Item(new Item.Settings())
    );

    public static final Item TanzaniteCrystal = registerItem(
        "tanzanite_crystal",
        new Item(new Item.Settings())
    );

    public static final Item GrownStaff = registerItem(
        "grown_staff",
        new GrownStaff(new Item.Settings().maxCount(1))
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
        "agony_wand",
        new AgonyWand(new Item.Settings().maxCount(1).maxDamage(250), "agony", 3)
    );

    public static final Item AnalyzeWand = registerItem(
        "analyze_wand",
        new AnalyzeWand(new Item.Settings().maxCount(1))
    );

    public static final Item HealingWand = registerItem(
        "healing_wand",
        new HealingWand(new Item.Settings().maxCount(1))
    );

    public static final Item BuffWand = registerItem(
        "buff_wand",
        new BuffWand(new Item.Settings().maxCount(1))
    );

    public static final Item BlueOre = registerItem(
        "blue_ore_ingot",
        new Item(new Item.Settings())
    );

    public static final Item HELIODOR_STAFF = registerItem(
        "heliodor_staff",
        new Item(new Item.Settings().maxCount(1))
    );

    public static final Item HELIODOR_SCRAP = registerItem(
        "heliodor_scrap",
        new Item(new Item.Settings())
    );

    public static final Item HELIODOR_SHARD = registerItem(
        "heliodor_shard",
        new Item(new Item.Settings())
    );

    public static final Item TANZANITE_HELMET = registerItem(
        "tanzanite_helmet",
        new ArmorItem(ModArmorMaterials.TANZANITE, ArmorItem.Type.HELMET, new Item.Settings())
    );

    public static final Item TANZANITE_CHESTPLATE = registerItem(
        "tanzanite_chestplate",
        new ArmorItem(ModArmorMaterials.TANZANITE, ArmorItem.Type.CHESTPLATE, new Item.Settings())
    );

    public static final Item TANZANITE_LEGGINGS = registerItem(
        "tanzanite_leggings",
        new ArmorItem(ModArmorMaterials.TANZANITE, ArmorItem.Type.LEGGINGS, new Item.Settings())
    );

    public static final Item TANZANITE_BOOTS = registerItem(
        "tanzanite_boots",
        new ArmorItem(ModArmorMaterials.TANZANITE, ArmorItem.Type.BOOTS, new Item.Settings())
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
            entries.add(TanzaniteScrap);
            entries.add(TanzaniteCrystal);
            entries.add(GrownStaff);
            entries.add(FirstWand);
            entries.add(LightBulletItem);
            entries.add(AgonyWand);
            entries.add(AnalyzeWand);
            entries.add(HealingWand);
            entries.add(BuffWand);
            entries.add(BlueOre);
            entries.add(TANZANITE_HELMET);
            entries.add(TANZANITE_CHESTPLATE);
            entries.add(TANZANITE_LEGGINGS);
            entries.add(TANZANITE_BOOTS);
        });
    }
}
