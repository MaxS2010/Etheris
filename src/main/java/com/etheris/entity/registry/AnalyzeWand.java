package com.etheris.entity.registry;

import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Locale;

public class AnalyzeWand extends Item {

    public AnalyzeWand(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.pass(stack);
        }

        if (user.isSneaking()) {
            printPlayerStats(user);
            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
        // TODO (Ярослав): без SHIFT по блоку — показ магічних параметрів блоку.
    }

    private void printPlayerStats(PlayerEntity player) {
        PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());

        player.sendMessage(Text.literal("    ✦ Magic attributes     ").formatted(Formatting.LIGHT_PURPLE), false);
        player.sendMessage(statLine("Ether", data.getEther(), Formatting.AQUA), false);
        player.sendMessage(statLine("Max Ether", data.getMaxEther(), Formatting.AQUA), false);
        player.sendMessage(statLine("Manipulation", data.getManipulation(), Formatting.GREEN), false);
        player.sendMessage(percentLine("Purity", data.getPurity(), Formatting.GOLD), false);

        System.out.printf(Locale.ROOT,
                "[AnalyzeWand] %s -> ether=%.1f, maxEther=%.1f, manipulation=%.1f, purity=%.1f%%%n",
                player.getName().getString(), data.getEther(), data.getMaxEther(),
                data.getManipulation(), data.getPurity());
    }

    private Text statLine(String label, float value, Formatting color) {
        return Text.literal(label + ": ").formatted(color)
                .append(Text.literal(String.format(Locale.ROOT, "%.1f", value)).formatted(Formatting.GOLD));
    }

    private Text percentLine(String label, float value, Formatting color) {
        return Text.literal(label + ": ").formatted(color)
                .append(Text.literal(String.format(Locale.ROOT, "%.1f%%", value)).formatted(Formatting.GOLD));
    }
}