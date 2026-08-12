package com.etheris.client.ui;

import com.etheris.player.PlayerMagicData;
import com.etheris.player.PlayerMagicManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HudExampleRenderer implements HudRenderCallback {
    private static final float CONTROL_BAR_VISIBLE = 5f;

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }

        PlayerMagicData data = PlayerMagicManager.getOrCreate(client.player.getUuid());
        float control = Math.max(0f, data.getManipulation());

        if (control < CONTROL_BAR_VISIBLE) {
            return;
        }

        float ether = data.getEther();
        float maxEther = Math.max(1f, data.getMaxEther());
        float purity = data.getPurity();

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int x = screenWidth - 142;
        int y = screenHeight - 42;

        drawEtherBar(context, x, y, 120, 10, ether / maxEther, purity);
    }

    private static void drawEtherBar(DrawContext context, int x, int y, int width, int height, float ratio, float purity) {
        int backgroundColor = 0x66000000;
        int borderColor = 0xFF2C3E50;
        int fillColor = getEtherColorByPurity(purity);

        context.fill(x, y, x + width, y + height, backgroundColor);
        context.fill(x - 1, y - 1, x + width + 1, y + height + 1, borderColor);

        int filledWidth = Math.max(0, Math.min(width - 2, (int) Math.round((width - 2) * Math.max(0f, Math.min(1f, ratio)))));
        context.fill(x + 1, y + 1, x + 1 + filledWidth, y + height - 1, fillColor);
    }

    private static int getEtherColorByPurity(float purity) {
        float p = Math.max(0f, Math.min(100f, purity));
        int section = (int) Math.floor(p / 10f);
        int brightness = Math.min(255, 40 + section * 35);

        int red = 18;
        int green = 90 + (section * 12);
        int blue = 255 - (section * 16);

        if (section >= 10) {
            red = 18;
            green = 255;
            blue = 255;
        }

        if (brightness < 0) {
            brightness = 0;
        }

        return (0xFF << 24) | (red << 16) | (green << 8) | blue;
    }
}
