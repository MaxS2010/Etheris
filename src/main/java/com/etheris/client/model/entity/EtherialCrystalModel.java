package com.etheris.client.model.entity;

import com.etheris.entity.mobs.EtherialCrystalEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EtherialCrystalModel<T extends EtherialCrystalEntity> extends Model {
    public static final ModelData MODEL_DATA = new ModelData();
    public static final ModelPartData ROOT = MODEL_DATA.getRoot();

    static {
        ModelPartData body = ROOT.addChild("body", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f)
                .uv(0, 16).cuboid(-3.0f, -9.0f, -3.0f, 6.0f, 1.0f, 6.0f), // верхня частина
                ModelTransform.NONE);

        // Додайте інші частини моделі за необхідності
        ROOT.addChild("head", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-3.0f, -6.0f, -3.0f, 6.0f, 6.0f, 6.0f),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));
    }

    public static final Identifier LAYER = Identifier.of("minecraft", "entity/generic");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;

    public EtherialCrystalModel(ModelPart root) {
        super(RenderLayer::getEntityCutoutNoCull);
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
    }

    public void setAngles(EtherialCrystalEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yaw = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.pitch = headPitch * ((float) Math.PI / 180.0F);
    }

    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        this.root.render(matrices, vertexConsumer, light, overlay);
    }
}
