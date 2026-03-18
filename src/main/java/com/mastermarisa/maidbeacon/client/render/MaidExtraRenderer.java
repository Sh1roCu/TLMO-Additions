package com.mastermarisa.maidbeacon.client.render;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.data.ExtraRenderSettings;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.entity.ExtraRenderingEntity;
import com.mastermarisa.maidbeacon.utils.MaidUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

import java.awt.*;
import java.util.List;

public class MaidExtraRenderer extends EntityRenderer<ExtraRenderingEntity> {
    private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation("minecraft", "textures/entity/beacon_beam.png");
    private static final Color BLUE_TRANS = new Color(181, 221, 237, 10);
    private static final Color WHITE = new Color(1, 1, 1, 0.6F);

    public MaidExtraRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ExtraRenderingEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        EntityMaid maid = entity.getMaid();
        if (maid == null) return;
        ExtraRenderSettings settings = ExtraRenderSettings.getOrCreate(maid);
        if (!settings.enableRendering) return;
        ItemStack itemStack = MaidUtils.getEquippedMobileBeacon(maid);
        if (itemStack.isEmpty()) return;
        MobileBeaconData mobileBeaconData = MobileBeaconData.getOrDefault(itemStack);

        if (settings.enableBeaconBeam)
            renderBeaconBeam(
                    poseStack,
                    bufferSource,
                    partialTick,
                    maid
            );

        if (settings.enableBeaconModel) {
            BeaconRenderHelper.renderBeaconModel(
                    poseStack,
                    bufferSource,
                    packedLight,
                    maid,
                    partialTick
            );
        }

        VertexConsumer haloConsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(BEAM_TEXTURE));

        if (settings.enableHeadHalo)
            renderHeadHalo(
                    poseStack,
                    haloConsumer,
                    maid
            );

        if (settings.enableSkyHalo) {
            renderSkyHalos(
                    poseStack,
                    haloConsumer,
                    maid,
                    mobileBeaconData.getLevel()
            );
        }
    }

    private void renderBeaconBeam(PoseStack poseStack, MultiBufferSource bufferSource, float partialTick, EntityMaid maid) {
        BeaconRenderHelper.renderBeaconBeam(
                poseStack,
                bufferSource,
                partialTick,
                1F,
                maid.level().getGameTime(),
                2, 128,
                Color.WHITE,
                0.06F, 0.06F,
                maid
        );
    }

    private void renderHeadHalo(PoseStack poseStack, VertexConsumer consumer, EntityMaid maid) {

    }

    private void renderSkyHalos(PoseStack poseStack, VertexConsumer consumer, EntityMaid maid, int level) {
        HaloRenderer.renderPart(
                poseStack,
                consumer,
                maid.level().getGameTime() + 170,
                3,
                38,
                0.5,
                0.33,
                2,
                List.of(
                        new HaloRenderer.AngleInfo(0, WHITE.getRGB()),
                        new HaloRenderer.AngleInfo(Math.toRadians(180), BLUE_TRANS.getRGB()),
                        new HaloRenderer.AngleInfo(Math.toRadians(362), BLUE_TRANS.getRGB())
                ),
                true,
                new Vector3f(0, 97, 0),
                null
        );

        HaloRenderer.renderPart(
                poseStack,
                consumer,
                maid.level().getGameTime() + 80,
                4.5,
                33.8,
                0.5,
                0.33,
                2,
                List.of(
                        new HaloRenderer.AngleInfo(0, BLUE_TRANS.getRGB()),
                        new HaloRenderer.AngleInfo(Math.toRadians(122), WHITE.getRGB())
                ),
                false,
                new Vector3f(0, 97F, 0),
                null
        );

        HaloRenderer.renderPart(
                poseStack,
                consumer,
                maid.level().getGameTime(),
                1.5,
                63.5,
                0.5,
                0.33,
                2,
                List.of(
                        new HaloRenderer.AngleInfo(0, WHITE.getRGB()),
                        new HaloRenderer.AngleInfo(Math.toRadians(180), BLUE_TRANS.getRGB()),
                        new HaloRenderer.AngleInfo(Math.toRadians(362), BLUE_TRANS.getRGB())
                ),
                true,
                new Vector3f(0, 98, 0),
                null
        );

        HaloRenderer.renderStatic(
                poseStack,
                consumer,
                74.5,
                0.4,
                0.25,
                2,
                BLUE_TRANS,
                new Vector3f(0, 97, 0),
                null
        );

        HaloRenderer.renderPart(
                poseStack,
                consumer,
                maid.level().getGameTime(),
                1,
                119,
                0.6,
                0.4,
                2,
                List.of(
                        new HaloRenderer.AngleInfo(0, BLUE_TRANS.getRGB()),
                        new HaloRenderer.AngleInfo(Math.toRadians(180), BLUE_TRANS.getRGB()),
                        new HaloRenderer.AngleInfo(Math.toRadians(362), WHITE.getRGB())
                ),
                false,
                new Vector3f(0, 100, 0),
                null
        );

        long gameTime = maid.level().getGameTime();
        double angle = (double) (gameTime % 1080) / 1080 * 2 * Math.PI;
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        HaloRenderer.renderStatic(
                poseStack,
                consumer,
                26,
                0.6,
                0.4,
                2,
                BLUE_TRANS,
                new Vector3f((float) (122 * cos), 99F, (float) (122 * sin)),
                null
        );

        angle = (double) ((gameTime + 980) % 1580) / 1580 * 2 * Math.PI;
        cos = Math.cos(angle);
        sin = Math.sin(angle);

        HaloRenderer.renderStatic(
                poseStack,
                consumer,
                20,
                0.6,
                0.4,
                2,
                BLUE_TRANS,
                new Vector3f(-(float) (129 * cos), 99F, -(float) (129 * sin)),
                null
        );
    }

    private void renderStarTrails(PoseStack poseStack, VertexConsumer consumer, EntityMaid maid, int level) {

    }

    @Override
    public boolean shouldRender(ExtraRenderingEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(ExtraRenderingEntity entity) {
        return BEAM_TEXTURE;
    }
}
