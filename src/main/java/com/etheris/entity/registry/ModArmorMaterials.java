package com.etheris.entity.registry;

import java.util.List;
import java.util.Map;

import com.etheris.Etheris;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;

public class ModArmorMaterials {

    public static final RegistryEntry<ArmorMaterial> TANZANITE = register(
        "tanzanite",
        new ArmorMaterial(
            Map.of(
                ArmorItem.Type.HELMET, 3,
                ArmorItem.Type.CHESTPLATE, 8,
                ArmorItem.Type.LEGGINGS, 6,
                ArmorItem.Type.BOOTS, 3,
                ArmorItem.Type.BODY, 0
            ),
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.ofItems(ModItems.TanzaniteCrystal),
            List.of(new ArmorMaterial.Layer(Etheris.id("tanzanite"))),
            2.0f,
            0.1f
        )
    );

    private static RegistryEntry<ArmorMaterial> register(String name, ArmorMaterial material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Etheris.id(name), material);
    }
}