package com.etheris.client;

import com.etheris.entity.projectiles.LightBulletEntity;
import com.etheris.entity.registry.ModItems;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class LightBulletEntityRenderer extends FlyingItemEntityRenderer<LightBulletEntity> {
    public LightBulletEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}