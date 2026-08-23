package com.etheris.entity.registry;

import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class GrownStaff extends Item {
    private static final int ETHER_INTERVAL = 5;
    private static final int GROWTH_INTERVAL = 20;
    private static final float ETHER_COST = 1.0f;

    public GrownStaff(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient() || !(user instanceof PlayerEntity player)) {
            return;
        }

        int elapsedTicks = getMaxUseTime(stack, user) - remainingUseTicks;
        if (elapsedTicks % ETHER_INTERVAL != 0) {
            return;
        }

        PlayerMagicData magicData = PlayerMagicManager.getOrCreate(player.getUuid());
        if (!magicData.consumeEther(ETHER_COST)) {
            return;
        }

        PlayerMagicManager.markEtherSpent(player.getUuid());
        if (elapsedTicks % GROWTH_INTERVAL == 0) {
            growNearbyPlants(world, player);
        }
    }

    private void growNearbyPlants(World world, PlayerEntity player) {
        BlockPos center = player.getBlockPos();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos plantPos = center.add(x, 0, z);
                BlockHitResult hitResult = new BlockHitResult(
                        plantPos.toCenterPos(), Direction.UP, plantPos, false);
                Hand activeHand = player.getActiveHand();
                ItemStack staffStack = player.getStackInHand(activeHand);
                player.setStackInHand(activeHand, new ItemStack(Items.BONE_MEAL));
                ((BoneMealItem) Items.BONE_MEAL).useOnBlock(
                    new net.minecraft.item.ItemUsageContext(player, activeHand, hitResult));
                player.setStackInHand(activeHand, staffStack);
            }
        }
    }
}