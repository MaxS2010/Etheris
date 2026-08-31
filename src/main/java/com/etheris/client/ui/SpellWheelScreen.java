package com.etheris.client.ui;

import com.etheris.entity.registry.ModItems;
import com.etheris.network.ModNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class SpellWheelScreen extends Screen {
    private static final StaffProfile[] STAFF_PROFILES = {
            new StaffProfile(ModItems.GrownStaff, 1),
            new StaffProfile(ModItems.FirstWand, 1),
            new StaffProfile(ModItems.AgonyWand, 2),
            new StaffProfile(ModItems.HealingWand, 2),
            new StaffProfile(ModItems.AnalyzeWand, 1),
            new StaffProfile(ModItems.BuffWand, 1)
    };
    private static final int RADIUS = 74;
    private final StaffProfile staffProfile;
    private int selectedSlot;

    public SpellWheelScreen() {
        super(Text.translatable("screen.etheris.spell_wheel"));
        this.staffProfile = findProfile(MinecraftClient.getInstance().player);
    }

    public static boolean canOpen(MinecraftClient client) {
        return client.player != null && findProfile(client.player) != null;
    }

    private static StaffProfile findProfile(PlayerEntity player) {
        if (player == null) {
            return null;
        }

        Item heldItem = player.getMainHandStack().getItem();
        for (StaffProfile profile : STAFF_PROFILES) {
            if (profile.item == heldItem) {
                return profile;
            }
        }

        heldItem = player.getOffHandStack().getItem();
        for (StaffProfile profile : STAFF_PROFILES) {
            if (profile.item == heldItem) {
                return profile;
            }
        }
        return null;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        updateSelection(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        updateSelection(mouseX, mouseY);
        selectAndClose();
        return true;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_G || keyCode == GLFW.GLFW_KEY_U) {
            selectAndClose();
            return true;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    private void updateSelection(double mouseX, double mouseY) {
        double deltaX = mouseX - width / 2.0;
        double deltaY = mouseY - height / 2.0;
        if (deltaX * deltaX + deltaY * deltaY < 16.0) {
            return;
        }

        double angle = Math.atan2(deltaY, deltaX) + Math.PI / 4.0;
        int slot = Math.floorMod((int) Math.floor(angle / (Math.PI / 2.0)), 4);
        if (slot < staffProfile.skillCount) {
            selectedSlot = slot;
        }
    }

    private void selectAndClose() {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            ClientPlayNetworking.send(new ModNetworking.SelectSpellPayload(selectedSlot));
        }
        close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int centerX = width / 2;
        int centerY = height / 2;
        context.fill(0, 0, width, height, 0x66000000);
        context.fill(centerX - 32, centerY - 32, centerX + 32, centerY + 32, 0xAA101820);
        context.drawCenteredTextWithShadow(textRenderer, staffProfile.name, centerX, centerY - 10, 0xFFFFFFFF);

        for (int slot = 0; slot < 4; slot++) {
            if (slot >= staffProfile.skillCount) {
                continue;
            }

            double angle = -Math.PI / 4.0 + slot * Math.PI / 2.0;
            int itemX = centerX + (int) (Math.cos(angle) * RADIUS) - 8;
            int itemY = centerY + (int) (Math.sin(angle) * RADIUS) - 8;
            int backgroundColor = slot == selectedSlot ? 0xFF55C2FF : 0xCC27313A;
            context.fill(itemX - 8, itemY - 8, itemX + 24, itemY + 24, backgroundColor);
            context.drawCenteredTextWithShadow(textRenderer, Integer.toString(slot + 1), itemX + 8, itemY + 9, 0xFFFFFFFF);
        }
    }

    private static final class StaffProfile {
        private final Item item;
        private final Text name;
        private final int skillCount;

        private StaffProfile(Item item, int skillCount) {
            this.item = item;
            this.name = item.getName();
            this.skillCount = skillCount;
        }
    }
}