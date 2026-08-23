package com.etheris.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ExampleScreen extends Screen {
    public ExampleScreen() {
        super(Text.literal("Etheris UI"));
    }

    @Override
    protected void init() {
        super.init();

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Action"), button -> {
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Etheris UI button clicked"));
                })
                .dimensions(this.width / 2 - 100, this.height / 2, 200, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> this.close())
                .dimensions(this.width / 2 - 100, this.height / 2 + 30, 200, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 30, 0xFFD166);
        context.drawTextWithShadow(this.textRenderer, Text.literal("This is a custom UI example for Etheris."), 20, 60, 0xFFFFFF);
    }
}
