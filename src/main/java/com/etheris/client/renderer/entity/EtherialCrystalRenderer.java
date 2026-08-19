package com.etheris.client.renderer.entity;

import com.etheris.Etheris;
import com.etheris.client.model.entity.EtherialCrystalModel;
import com.etheris.entity.mobs.EtherialCrystalEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EtherialCrystalRenderer extends EntityRenderer<EtherialCrystalEntity> {

    private static final Identifier TEXTURE = Identifier.of(Etheris.MOD_ID, "textures/entity/ethereal_crystal.png");
        public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(
            Identifier.of(Etheris.MOD_ID, "ethereal_crystal"), "main");
    private final EtherialCrystalModel<EtherialCrystalEntity> model;

    public EtherialCrystalRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new EtherialCrystalModel<>(context.getPart(MODEL_LAYER));
    }

    @Override
    public Identifier getTexture(EtherialCrystalEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(EtherialCrystalEntity entity, float yaw, float tickDelta, MatrixStack matrices, net.minecraft.client.render.VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(1.0F, 1.0F, 1.0F);
        net.minecraft.client.render.VertexConsumer vertexConsumer = vertexConsumers.getBuffer(net.minecraft.client.render.RenderLayer.getEntityCutoutNoCull(this.getTexture(entity)));
        model.render(matrices, vertexConsumer, light, net.minecraft.client.render.entity.LivingEntityRenderer.getOverlay(entity, 0.0F), 0xFFFFFF);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
