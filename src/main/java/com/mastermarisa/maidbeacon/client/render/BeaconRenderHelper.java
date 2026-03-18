package com.mastermarisa.maidbeacon.client.render;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.*;

public class BeaconRenderHelper {
    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("minecraft", "textures/entity/beacon_beam.png");

    public static void renderBeaconBeam(PoseStack poseStack, MultiBufferSource bufferSource, float partialTick, float textureScale, long gameTime, int yOffset, int height, Color color, float beamRadius, float glowRadius, EntityMaid maid) {
        BeaconRenderHelper.renderBeaconBeam(poseStack, bufferSource, BEAM_LOCATION, partialTick, textureScale, gameTime, yOffset, height, color.getRGBColorComponents(new float[3]), beamRadius, glowRadius, maid);
    }

    private static void renderBeaconBeam(PoseStack poseStack, MultiBufferSource bufferSource, ResourceLocation location, float partialTick, float textureScale, long gameTime, int yOffset, int height, float[] colors, float beamRadius, float glowRadius, EntityMaid maid) {
        int i = yOffset + height;
        poseStack.pushPose();
        double offset = -0.72;
        if (maid.isMaidInSittingPose()) {
            offset -= 0.6;
        }
        poseStack.translate(0.0, offset, 0.0);
        float f = Math.floorMod(gameTime, 40) + partialTick;
        float f1 = height < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2f - Mth.floor(f1 * 0.1f));
        float f3 = colors[0];
        float f4 = colors[1];
        float f5 = colors[2];
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 2.25f - 45.0f));
        float f9 = -beamRadius;
        float f12 = -beamRadius;
        float f15 = -1.0f + f2;
        float f16 = height * textureScale * (0.5f / beamRadius) + f15;
        BeaconRenderHelper.renderPart(poseStack, bufferSource.getBuffer(RenderType.beaconBeam(location, false)), f3, f4, f5, 1.0f, yOffset, i, 0.0f, beamRadius, beamRadius, 0.0f, f9, 0.0f, 0.0f, f12, 0.0f, 1.0f, f16, f15);
        poseStack.popPose();
        float f6 = -glowRadius;
        float f7 = -glowRadius;
        float f8 = -glowRadius;
        f9 = -glowRadius;
        f15 = -1.0f + f2;
        f16 = height * textureScale + f15;
        BeaconRenderHelper.renderPart(poseStack, bufferSource.getBuffer(RenderType.beaconBeam(location, true)), f3, f4, f5, 0.125f, yOffset, i, f6, f7, glowRadius, f8, f9, glowRadius, glowRadius, glowRadius, 0.0f, 1.0f, f16, f15);
        poseStack.popPose();
    }

    private static void renderPart(PoseStack poseStack, VertexConsumer consumer, float red, float green, float blue, float alpha, int minY, int maxY, float x0, float z0, float x1, float z1, float x2, float z2, float x3, float z3, float minU, float maxU, float minV, float maxV) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        renderQuad(matrix4f, matrix3f, consumer, red, green, blue, alpha, minY, maxY, x0, z0, x1, z1, minU, maxU, minV, maxV);
        renderQuad(matrix4f, matrix3f, consumer, red, green, blue, alpha, minY, maxY, x3, z3, x2, z2, minU, maxU, minV, maxV);
        renderQuad(matrix4f, matrix3f, consumer, red, green, blue, alpha, minY, maxY, x1, z1, x3, z3, minU, maxU, minV, maxV);
        renderQuad(matrix4f, matrix3f, consumer, red, green, blue, alpha, minY, maxY, x2, z2, x0, z0, minU, maxU, minV, maxV);
    }

    private static void renderQuad(Matrix4f pose, Matrix3f normal, VertexConsumer consumer, float red, float green, float blue, float alpha, int minY, int maxY, float minX, float minZ, float maxX, float maxZ, float minU, float maxU, float minV, float maxV) {
        addVertex(pose, normal, consumer, red, green, blue, alpha, maxY, minX, minZ, maxU, minV);
        addVertex(pose, normal, consumer, red, green, blue, alpha, minY, minX, minZ, maxU, maxV);
        addVertex(pose, normal, consumer, red, green, blue, alpha, minY, maxX, maxZ, minU, maxV);
        addVertex(pose, normal, consumer, red, green, blue, alpha, maxY, maxX, maxZ, minU, minV);
    }

    private static void addVertex(Matrix4f pose, Matrix3f normal, VertexConsumer consumer, float red, float green, float blue, float alpha, int y, float x, float z, float u, float v) {
        consumer.vertex(pose, x, (float) y, z).color(red, green, blue, alpha).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public static void renderBeaconModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntityMaid entity, float partialTick) {
        poseStack.pushPose();
        poseStack.translate(0.0, entity.getBbHeight() + (entity.isMaidInSittingPose() ? -0.1 : 0.4) - 1.0, 0.0);
        long gameTime = entity.level().getGameTime();
        float rotation = (gameTime + partialTick) * 3.0f;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.scale(1.0f, 1.0f, 1.0f);
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.BEACON), ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), entity.getId());
        poseStack.popPose();
    }
}
