package com.mastermarisa.maidbeacon.client.render;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class BeaconRenderer {
    public static final ResourceLocation BEAM_LOCATION = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/beacon_beam.png");

    public static void renderBeaconBeam(PoseStack poseStack, MultiBufferSource bufferSource, float partialTick, float textureScale, long gameTime,
                                        int yOffset, int height, int color, float beamRadius, float glowRadius, @Nullable Quaternionf quaternion, EntityMaid maid) {
        int i = yOffset + height;
        poseStack.pushPose();
        double offset = 0.28D;
        if (maid.isMaidInSittingPose()) offset -= 0.6D;
        poseStack.translate(0, offset, 0);
        if (quaternion != null) poseStack.mulPose(quaternion);
        float f = (float) Math.floorMod(gameTime, 40) + partialTick;
        float f1 = height < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2F - (float) Mth.floor(f1 * 0.1F));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));
        float f3;
        float f5;
        float f6 = -beamRadius;
        float f9 = -beamRadius;
        float f12 = -1.0F + f2;
        float f13 = (float) height * textureScale * (0.5F / beamRadius) + f12;
        net.minecraft.client.renderer.blockentity.BeaconRenderer.renderPart(poseStack, bufferSource.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false)), color, yOffset, i, 0.0F, beamRadius, beamRadius, 0.0F, f6, 0.0F, 0.0F, f9, 0.0F, 1.0F, f13, f12);
        poseStack.popPose();
        f3 = -glowRadius;
        float f4 = -glowRadius;
        f5 = -glowRadius;
        f6 = -glowRadius;
        f12 = -1.0F + f2;
        f13 = (float) height * textureScale + f12;
        net.minecraft.client.renderer.blockentity.BeaconRenderer.renderPart(poseStack, bufferSource.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, true)), FastColor.ARGB32.color(32, color), yOffset, i, f3, f4, glowRadius, f5, f6, glowRadius, glowRadius, glowRadius, 0.0F, 1.0F, f13, f12);
        poseStack.popPose();
    }

    public static void renderBeaconModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntityMaid entity, float partialTick) {
        poseStack.pushPose();
        poseStack.translate(0.0F, (double) entity.getBbHeight() + (entity.isMaidInSittingPose() ? -0.1 : 0.4) - 1, 0.0F);
        long gameTime = entity.level().getGameTime();
        float rotation = ((float) gameTime + partialTick) * 3.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.scale(1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.BEACON), ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), entity.getId());
        poseStack.popPose();
    }
}
