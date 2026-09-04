package com.etheris.client.ui;

import com.etheris.network.ModNetworking;
import com.etheris.player.PlayerMagicData;
import com.etheris.playerclass.ClassDefinition;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClassesScreen extends Screen {

    private final PlayerMagicData magicData;

    public ClassesScreen(PlayerMagicData magicData) {
        super(Text.literal("Classes"));
        this.magicData = magicData;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, "CLASSES", this.width / 2, 20, 0xFFFFFF);

        int cols = 5;
        int slotSize = 40;
        int spacing = 10;
        int startX = (this.width - (cols * (slotSize + spacing) - spacing)) / 2;
        int startY = 55;

        ClassDefinition[] classes = ClassDefinition.ALL;

        for (int i = 0; i < classes.length; i++) {
            ClassDefinition cls = classes[i];
            int col = i % cols;
            int row = i / cols;

            int x = startX + col * (slotSize + spacing);
            int y = startY + row * (slotSize + spacing);

            boolean canAfford = magicData.getManipulation() >= cls.manipulationCost();
            int borderColor = canAfford ? 0xFFFFAA00 : 0xFF555555;

            context.fill(x, y, x + slotSize, y + slotSize, 0xCC000000);
            context.drawBorder(x, y, slotSize, slotSize, borderColor);

            context.drawItem(new ItemStack(cls.icon()), x + (slotSize - 16) / 2, y + (slotSize - 16) / 2);

            if (mouseX >= x && mouseX <= x + slotSize && mouseY >= y && mouseY <= y + slotSize) {
                List<Text> tooltip = new ArrayList<>();
                tooltip.add(Text.literal("§6§l" + cls.name()));
                for (String line : cls.description().split("\n")) {
                    tooltip.add(Text.literal("§7" + line));
                }
                tooltip.add(Text.literal(""));

                if (canAfford) {
                    tooltip.add(Text.literal("§eCost: §a" + cls.manipulationCost() + " Manipulation"));
                    tooltip.add(Text.literal("§aClick to select"));
                } else {
                    tooltip.add(Text.literal("§cCost: " + cls.manipulationCost() + " Manipulation (Not enough!)"));
                }

                context.drawTooltip(this.textRenderer, tooltip, mouseX, mouseY);
            }
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int cols = 5;
        int slotSize = 40;
        int spacing = 10;
        int startX = (this.width - (cols * (slotSize + spacing) - spacing)) / 2;
        int startY = 55;

        ClassDefinition[] classes = ClassDefinition.ALL;

        for (int i = 0; i < classes.length; i++) {
            ClassDefinition cls = classes[i];
            int col = i % cols;
            int row = i / cols;

            int x = startX + col * (slotSize + spacing);
            int y = startY + row * (slotSize + spacing);

            if (mouseX >= x && mouseX <= x + slotSize && mouseY >= y && mouseY <= y + slotSize) {
                if (magicData.getManipulation() >= cls.manipulationCost()) {
                    ClientPlayNetworking.send(new ModNetworking.SelectClassPayload(cls.id()));
                    this.close();
                } else {
                    MinecraftClient.getInstance().getSoundManager().play(
                            PositionedSoundInstance.master(SoundEvents.ENTITY_VILLAGER_NO, 1.0F));
                }
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}