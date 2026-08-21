package com.etheris.entity.registry;

import com.etheris.block.ModBlocks;
import com.etheris.block.ModNatureRegistry;
import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Locale;

public class AnalyzeWand extends Item {

    public AnalyzeWand(Settings settings) {
        super(settings);
    }

    /**
     * Вызывается при нажатии ПКМ по блоку.
     */
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();

        if (world.isClient() || player == null) {
            return ActionResult.PASS;
        }

        // Зажатый SHIFT — вывод параметров игрока
        if (player.isSneaking()) {
            printPlayerStats(player);
            return ActionResult.SUCCESS;
        }

        // Клик без SHIFT — анализируем блок
        BlockPos pos = context.getBlockPos();
        printBlockStats(player, world, pos);

        return ActionResult.SUCCESS;
    }

    /**
     * Вызывается при клике ПКМ в воздух (или при пропуске useOnBlock).
     */
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

    private void printBlockStats(PlayerEntity player, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        String blockName = block.getName().getString();

        float ether = 0.0f;
        float purity = 0.0f;
        boolean hasMagic = false;

        // 1. Проверяем регистрацию через ModNatureRegistry
        if (ModNatureRegistry.isMagicNature(block)) {
            ModNatureRegistry.NatureParams params = ModNatureRegistry.getParams(block);
            ether = params.ether();
            purity = params.purity();
            hasMagic = true;
        } 
        // 2. Если в реестре нет, проверяем прямые типы блоков (EtherBlock / EtherPillarBlock)
        else if (block instanceof ModBlocks.EtherBlock etherBlock) {
            ether = etherBlock.getEther();
            purity = etherBlock.getPurity();
            hasMagic = true;
        } else if (block instanceof ModBlocks.EtherPillarBlock pillarBlock) {
            ether = pillarBlock.getEther();
            purity = pillarBlock.getPurity();
            hasMagic = true;
        }

        // Вывод результатов в чат
        player.sendMessage(Text.literal("    ✦ Block Analysis ✦    ").formatted(Formatting.DARK_PURPLE), false);
        player.sendMessage(Text.literal("Target: ").formatted(Formatting.GRAY)
                .append(Text.literal(blockName).formatted(Formatting.WHITE)), false);

        if (hasMagic) {
            player.sendMessage(statLine("Ether", ether, Formatting.AQUA), false);
            player.sendMessage(percentLine("Purity", purity * 100f, Formatting.GOLD), false);
        } else {
            player.sendMessage(Text.literal("This block contains no magical energy.").formatted(Formatting.DARK_GRAY), false);
        }
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