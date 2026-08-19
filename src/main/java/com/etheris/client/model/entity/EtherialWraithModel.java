package com.etheris.client.model.entity;

import com.etheris.entity.mobs.EtherialWraithEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EtherialWraithModel<T extends EtherialWraithEntity> extends Model {
    public static final ModelData MODEL_DATA = new ModelData();
    public static final ModelPartData ROOT = MODEL_DATA.getRoot();

    static {
        ModelPartData body = ROOT.addChild("body", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-3.0f, -6.0f, -3.0f, 6.0f, 12.0f, 6.0f),
                ModelTransform.NONE);

        ModelPartData head = body.addChild("head", ModelPartBuilder.create()
                .uv(0, 18).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f),
                ModelTransform.pivot(0.0f, -6.0f, 0.0f));

        ModelPartData leftArm = body.addChild("left_arm", ModelPartBuilder.create()
                .uv(24, 0).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f),
                ModelTransform.pivot(3.0f, -4.0f, 0.0f));

        ModelPartData rightArm = body.addChild("right_arm", ModelPartBuilder.create()
                .uv(24, 0).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f),
                ModelTransform.pivot(-3.0f, -4.0f, 0.0f));
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(MODEL_DATA, 64, 64);
    }

    public static final Identifier LAYER = Identifier.of("minecraft", "entity/generic");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public EtherialWraithModel(ModelPart root) {
        super(RenderLayer::getEntityCutoutNoCull);
        this.root = root;
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.leftArm = this.body.getChild("left_arm");
        this.rightArm = this.body.getChild("right_arm");
    }

    public void setAngles(EtherialWraithEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yaw = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.pitch = headPitch * ((float) Math.PI / 180.0F);

        this.leftArm.pitch = -0.75F + limbSwingAmount * 0.3f;
        this.rightArm.pitch = -0.75F - limbSwingAmount * 0.3f;
    }

    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        this.root.render(matrices, vertexConsumer, light, overlay);
    }
}
