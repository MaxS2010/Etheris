package com.etheris.client;

import com.etheris.client.LightBulletEntityRenderer;
import com.etheris.entity.projectiles.ModProjectiles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class EtherisClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(
                ModProjectiles.LIGHT_BULLET,
                LightBulletEntityRenderer::new
        );
    }
}
