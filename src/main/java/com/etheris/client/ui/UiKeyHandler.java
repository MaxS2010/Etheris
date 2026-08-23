package com.etheris.client.ui;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class UiKeyHandler {
    private static final KeyBinding OPEN_UI = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.etheris.open_ui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_U,
            "category.etheris.ui"
    ));

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_UI.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new ExampleScreen());
            }
        });
    }
}
