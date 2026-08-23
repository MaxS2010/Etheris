package com.etheris.client.renderer.entity;

import com.etheris.Etheris;
import com.etheris.client.model.entity.EtherialWraithModel;
import com.etheris.entity.mobs.EtherialWraithEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EtherialWraithRenderer extends EntityRenderer<EtherialWraithEntity> {

    private static final Identifier TEXTURE = Identifier.of(Etheris.MOD_ID, "textures/entity/ethereal_wraith.png");
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(
            Identifier.of(Etheris.MOD_ID, "ethereal_wraith"), "main");
    private final EtherialWraithModel<EtherialWraithEntity> model;

    public EtherialWraithRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new EtherialWraithModel<>(context.getPart(MODEL_LAYER));
    }

    @Override
    public Identifier getTexture(EtherialWraithEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(EtherialWraithEntity entity, float yaw, float tickDelta, MatrixStack matrices, net.minecraft.client.render.VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(1.2F, 1.2F, 1.2F);
        net.minecraft.client.render.VertexConsumer vertexConsumer = vertexConsumers.getBuffer(net.minecraft.client.render.RenderLayer.getEntityCutoutNoCull(this.getTexture(entity)));
        model.render(matrices, vertexConsumer, light, net.minecraft.client.render.entity.LivingEntityRenderer.getOverlay(entity, 0.0F), 0xFFFFFF);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
