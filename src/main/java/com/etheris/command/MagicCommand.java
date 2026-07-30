package com.etheris.command;

import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;

import java.util.Locale;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MagicCommand {
    public static void initializeCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("modInfo")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("Magic Mod"), false);
                    return 1;
                })
            );

            dispatcher.register(
                CommandManager.literal("etheris")
                    .then(CommandManager.literal("get")
                        .then(CommandManager.literal("ether")
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player == null) {
                                    context.getSource().sendFeedback(() -> Text.literal("This command can only be used by a player."), false);
                                    return 1;
                                }

                                PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                String value = String.format(Locale.ROOT, "%.1f", data.getEther());
                                context.getSource().sendFeedback(() -> Text.literal("✦ Ether: ").formatted(Formatting.AQUA)
                                        .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                return 1;
                            })
                        )
                    )
                    .then(CommandManager.literal("spend")
                        .then(CommandManager.literal("ether")
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player == null) {
                                    context.getSource().sendFeedback(() -> Text.literal("This command can only be used by a player."), false);
                                    return 1;
                                }

                                PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                boolean success = data.consumeEther(10f);

                                if (success) {
                                    context.getSource().sendFeedback(() -> Text.literal("✦ Spent 10 ether. Current: ").formatted(Formatting.AQUA)
                                            .append(Text.literal(String.format(Locale.ROOT, "%.1f", data.getEther())).formatted(Formatting.GOLD)), false);
                                } else {
                                    context.getSource().sendFeedback(() -> Text.literal("✕ Not enough ether to spend.").formatted(Formatting.RED), false);
                                }
                                return 1;
                            })
                        )
                    )
                    .then(CommandManager.literal("restore")
                        .then(CommandManager.literal("ether")
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player == null) {
                                    context.getSource().sendFeedback(() -> Text.literal("This command can only be used by a player."), false);
                                    return 1;
                                }

                                PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                data.setEther(data.getMaxEther());
                                context.getSource().sendFeedback(() -> Text.literal("✦ Ether restored to full.").formatted(Formatting.AQUA), false);
                                return 1;
                            })
                        )
                    )
            );
        });
    }
}
