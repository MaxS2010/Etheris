package com.etheris.client;

import com.etheris.client.ui.HudExampleRenderer;
import com.etheris.client.ui.UiKeyHandler;
import com.etheris.entity.projectiles.ModProjectiles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class EtherisClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(
                ModProjectiles.LIGHT_BULLET,
                LightBulletEntityRenderer::new
        );

        HudRenderCallback.EVENT.register(new HudExampleRenderer());
        UiKeyHandler.register();
    }
}
