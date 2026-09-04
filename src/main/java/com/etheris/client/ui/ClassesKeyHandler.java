package com.etheris.client.ui;

import com.etheris.player.PlayerMagicManager;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClassesKeyHandler {
    private static final KeyBinding OPEN_CLASSES = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.etheris.open_classes",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.etheris.ui"
    ));

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_CLASSES.wasPressed()) {
                if (client.currentScreen instanceof ClassesScreen) {
                    client.setScreen(null);
                } else if (client.currentScreen == null) {
                    client.setScreen(new ClassesScreen(PlayerMagicManager.getOrCreate(client.player.getUuid())));
                }
            }
        });
    }
}