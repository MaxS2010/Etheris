package com.etheris.command;

import java.util.Locale;
import com.etheris.playerclass.ClassPurchaseHandler;
import com.etheris.playerclass.PlayerClassData;
import com.etheris.playerclass.PlayerClassManager;
import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;
import com.etheris.playerclass.PlayerClassData;
import com.etheris.playerclass.PlayerClassManager;
import com.mojang.brigadier.arguments.FloatArgumentType;

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
                .then(CommandManager.literal("ether")
                    .then(CommandManager.literal("get")
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
                    .then(CommandManager.literal("ether")
                        .then(CommandManager.literal("spend")
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
                    .then(CommandManager.literal("ether")
                        .then(CommandManager.literal("restore")
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

                    .then(CommandManager.literal("manipulation")
                        // /etheris manipulation get
                        .then(CommandManager.literal("get")
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player == null) return 0;

                                PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                String value = String.format(Locale.ROOT, "%.1f", data.getManipulation());
                                context.getSource().sendFeedback(() -> Text.literal("✦ Manipulation: ").formatted(Formatting.AQUA)
                                        .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                return 1;
                            })
                        )
                        // /etheris manipulation add <amount>
                        .then(CommandManager.literal("add")
                            .then(CommandManager.argument("amount", FloatArgumentType.floatArg(0.1f))
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    if (player == null) return 0;

                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                    data.modifyManipulation(amount);

                                    String value = String.format(Locale.ROOT, "%.1f", data.getManipulation());
                                    context.getSource().sendFeedback(() -> Text.literal("✦ Added manipulation. Current: ").formatted(Formatting.GREEN)
                                            .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                    return 1;
                                })
                            )
                        )
                        // /etheris manipulation remove <amount>
                        .then(CommandManager.literal("remove")
                            .then(CommandManager.argument("amount", FloatArgumentType.floatArg(0.1f))
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    if (player == null) return 0;

                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                    data.modifyManipulation(-amount);

                                    String value = String.format(Locale.ROOT, "%.1f", data.getManipulation());
                                    context.getSource().sendFeedback(() -> Text.literal("✦ Removed manipulation. Current: ").formatted(Formatting.RED)
                                            .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                    return 1;
                                    
                                })
                            )
                        )
                    )
                    .then(CommandManager.literal("maxether")
                        .then(CommandManager.literal("get")
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player == null) return 0;

                                PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                String value = String.format(Locale.ROOT, "%.1f", data.getMaxEther());
                                context.getSource().sendFeedback(() -> Text.literal("✦ Max Ether: ").formatted(Formatting.AQUA)
                                        .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                return 1;
                            })
                        )
                        .then(CommandManager.literal("set")
                            .then(CommandManager.argument("amount", FloatArgumentType.floatArg(0.0f))
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    if (player == null) return 0;

                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                    data.setMaxEther(amount);

                                    String value = String.format(Locale.ROOT, "%.1f", data.getMaxEther());
                                    context.getSource().sendFeedback(() -> Text.literal("✦ Max Ether set to: ").formatted(Formatting.GREEN)
                                            .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                    return 1;
                                })
                            )
                        )
                    )
                    .then(CommandManager.literal("purity")
                        .then(CommandManager.literal("get")
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player == null) return 0;

                                PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                String value = String.format(Locale.ROOT, "%.1f%%", data.getPurity());
                                context.getSource().sendFeedback(() -> Text.literal("✦ Purity: ").formatted(Formatting.LIGHT_PURPLE)
                                        .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                return 1;
                            })
                        )
                        .then(CommandManager.literal("add")
                            .then(CommandManager.argument("amount", FloatArgumentType.floatArg(0.1f))
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    if (player == null) return 0;

                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                    data.modifyPurity(amount);

                                    String value = String.format(Locale.ROOT, "%.1f%%", data.getPurity());
                                    context.getSource().sendFeedback(() -> Text.literal("✦ Added purity. Current: ").formatted(Formatting.GREEN)
                                            .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                    return 1;
                                })
                            )
                        )
                        .then(CommandManager.literal("remove")
                            .then(CommandManager.argument("amount", FloatArgumentType.floatArg(0.1f))
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    if (player == null) return 0;

                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    PlayerMagicData data = PlayerMagicManager.getOrCreate(player.getUuid());
                                    data.modifyPurity(-amount);

                                    String value = String.format(Locale.ROOT, "%.1f%%", data.getPurity());
                                    context.getSource().sendFeedback(() -> Text.literal("✦ Removed purity. Current: ").formatted(Formatting.RED)
                                            .append(Text.literal(value).formatted(Formatting.GOLD)), false);
                                    return 1;
                                })
                                
                                
                            )
                        )
                    )
                    .then(CommandManager.literal("class")
    .then(CommandManager.literal("get")
        .executes(context -> {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (player == null) return 0;

            PlayerClassData classData = PlayerClassManager.getOrCreate(player.getUuid());
            if (classData.hasClass()) {
                context.getSource().sendFeedback(() -> Text.literal("✦ Current class: ").formatted(Formatting.AQUA)
                        .append(Text.literal(classData.getOwnedClass()).formatted(Formatting.GOLD)), false);
            } else {
                context.getSource().sendFeedback(() -> Text.literal("You have no class selected.").formatted(Formatting.GRAY), false);
            }
            return 1;
        })
    )
    .then(CommandManager.literal("reset")
        .executes(context -> {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (player == null) return 0;

            PlayerClassData classData = PlayerClassManager.getOrCreate(player.getUuid());
            classData.reset();
            ClassPurchaseHandler.clearClassEffects(player);

            context.getSource().sendFeedback(() -> Text.literal("✦ Your class has been reset.").formatted(Formatting.YELLOW), false);
            return 1;
        })
    )
)
            );
        });
    }
}