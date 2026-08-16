package com.etheris.client;

import com.etheris.client.renderer.entity.EtherialCrystalRenderer;
import com.etheris.client.renderer.entity.EtherialWraithRenderer;
import com.etheris.entity.mobs.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class EtherialCrystalClientInit {
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(ModEntities.ETHEREAL_CRYSTAL, EtherialCrystalRenderer::new);
        EntityRendererRegistry.register(ModEntities.ETHEREAL_WRAITH, EtherialWraithRenderer::new);
    }
}
