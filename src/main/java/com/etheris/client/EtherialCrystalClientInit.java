package com.etheris.client;

import com.etheris.client.renderer.entity.EtherialCrystalRenderer;
import com.etheris.client.renderer.entity.EtherialWraithRenderer;
import com.etheris.client.model.entity.EtherialWraithModel;
import com.etheris.client.model.entity.EtherialCrystalModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import com.etheris.entity.mobs.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class EtherialCrystalClientInit {
    public static void registerEntityRenderers() {
        EntityModelLayerRegistry.registerModelLayer(
            EtherialCrystalRenderer.MODEL_LAYER, EtherialCrystalModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(
            EtherialWraithRenderer.MODEL_LAYER, EtherialWraithModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ETHEREAL_CRYSTAL, EtherialCrystalRenderer::new);
        EntityRendererRegistry.register(ModEntities.ETHEREAL_WRAITH, EtherialWraithRenderer::new);
    }
}
