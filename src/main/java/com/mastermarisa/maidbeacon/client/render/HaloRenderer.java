package com.mastermarisa.maidbeacon.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class HaloRenderer {
    public static void renderSpaced(PoseStack poseStack, VertexConsumer consumer, long gameTime, double speed,
                                    double length, int count, double radius, double width, double height,
                                    double step, boolean clockwise, List<AngleInfo> colors, @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        double cycle = 360 / speed;
        double progress = gameTime % cycle / cycle;
        Quaternionf quaternionf = clockwise ? Axis.YP.rotation((float) (2 * Math.PI * progress)) : Axis.YN.rotation((float) (2 * Math.PI * progress));
        if (rotation != null) quaternionf = rotation.mul(quaternionf);
        renderSpacedStatic(poseStack, consumer, length, count, radius, width, height, step, colors, offset, quaternionf);
    }

    public static void renderSpaced(PoseStack poseStack, VertexConsumer consumer, long gameTime, double speed,
                                    double length, int count, double radius, double width, double height,
                                    double step, boolean clockwise, Color color, @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        double cycle = 360 / speed;
        double progress = gameTime % cycle / cycle;
        Quaternionf quaternionf = clockwise ? Axis.YP.rotation((float) (2 * Math.PI * progress)) : Axis.YN.rotation((float) (2 * Math.PI * progress));
        if (rotation != null) quaternionf = rotation.mul(quaternionf);
        renderSpacedStatic(poseStack, consumer, length, count, radius, width, height, step, color, offset, quaternionf);
    }

    public static void renderSpacedStatic(PoseStack poseStack, VertexConsumer consumer, double length, int count,
                                          double radius, double width, double height, double step, List<AngleInfo> colors,
                                          @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        poseStack.pushPose();
        if (offset != null) poseStack.translate(offset.x(), offset.y(), offset.z());
        if (rotation != null) poseStack.mulPose(rotation);

        double radiusInner = radius - width / 2;
        double radiusOuter = radius + width / 2;
        int segmentCount = (int) (360 / step);
        double spacing = (360 - length * count) / count;
        if (spacing <= 0 || length <= 0) {
            poseStack.popPose();
            return;
        }

        length = Math.toRadians(length);
        spacing = Math.toRadians(spacing);
        double left = length;
        double right = length + spacing;

        int index = 0;
        List<AngleInfo> angleInfos = new ArrayList<>();
        for (int i = 0; i < segmentCount; i++) {
            double angle = 2D * Math.PI * i / segmentCount;
            while (angle >= right + length) {
                left = right + length;
                right = left + spacing;
            }
            if (left < angle && angle < right) {
                angleInfos.add(null);
                continue;
            }

            while (index + 1 < colors.size() && colors.get(index + 1).angle <= angle) index++;
            AngleInfo pre = colors.get(index);
            AngleInfo post = index == colors.size() - 1 ? new AngleInfo(2D * Math.PI, colors.get(0).color) : colors.get(index + 1);
            Color color = lerp(new Color(pre.color, true), new Color(post.color, true), (angle - pre.angle) / (post.angle - pre.angle));
            angleInfos.add(new AngleInfo(angle, color.getRGB()));
        }

        for (int i = 0; i < angleInfos.size() - 1; i++) {
            if (angleInfos.get(i) == null || angleInfos.get(i + 1) == null) continue;
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusInner, (float) radiusOuter, 0, 0);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusOuter, (float) radius, 0, (float) height);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radius, (float) radiusInner, (float) height, 0);
        }
        if (angleInfos.get(angleInfos.size() - 1) != null && angleInfos.get(0) != null) {
            segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radiusInner, (float) radiusOuter, 0, 0);
            segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radiusOuter, (float) radius, 0, (float) height);
            segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radius, (float) radiusInner, (float) height, 0);
        }

        poseStack.popPose();
    }

    public static void renderSpacedStatic(PoseStack poseStack, VertexConsumer consumer, double length, int count,
                                          double radius, double width, double height, double step, Color color,
                                          @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        poseStack.pushPose();
        if (offset != null) poseStack.translate(offset.x(), offset.y(), offset.z());
        if (rotation != null) poseStack.mulPose(rotation);

        double radiusInner = radius - width / 2;
        double radiusOuter = radius + width / 2;
        int segmentCount = (int) (360 / step);
        double spacing = (360 - length * count) / count;
        if (spacing <= 0 || length <= 0) {
            poseStack.popPose();
            return;
        }

        length = Math.toRadians(length);
        spacing = Math.toRadians(spacing);
        double left = length;
        double right = length + spacing;

        List<AngleInfo> angleInfos = new ArrayList<>();
        for (int i = 0; i < segmentCount; i++) {
            double angle = 2D * Math.PI * i / segmentCount;
            while (angle >= right + length) {
                left = right + length;
                right = left + spacing;
            }
            if (left < angle && angle < right) angleInfos.add(null);
            else angleInfos.add(new AngleInfo(angle, color.getRGB()));
        }

        for (int i = 0; i < angleInfos.size() - 1; i++) {
            if (angleInfos.get(i) == null || angleInfos.get(i + 1) == null) continue;
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusInner, (float) radiusOuter, 0, 0);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusOuter, (float) radius, 0, (float) height);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radius, (float) radiusInner, (float) height, 0);
        }
        if (angleInfos.get(angleInfos.size() - 1) != null && angleInfos.get(0) != null) {
            segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radiusInner, (float) radiusOuter, 0, 0);
            segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radiusOuter, (float) radius, 0, (float) height);
            segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radius, (float) radiusInner, (float) height, 0);
        }

        poseStack.popPose();
    }

    public static void renderPart(PoseStack poseStack, VertexConsumer consumer, long gameTime, double speed,
                                  double radius, double width, double height, double step, List<AngleInfo> colors,
                                  boolean clockwise, @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        double cycle = 360 / speed;
        double progress = gameTime % cycle / cycle;
        Quaternionf quaternionf = clockwise ? Axis.YP.rotation((float) (2 * Math.PI * progress)) : Axis.YN.rotation((float) (2 * Math.PI * progress));
        if (rotation != null) quaternionf = rotation.mul(quaternionf);
        renderPartStatic(poseStack, consumer, radius, width, height, step, colors, offset, quaternionf);
    }

    public static void renderPartStatic(PoseStack poseStack, VertexConsumer consumer, double radius,
                                        double width, double height, double step, List<AngleInfo> colors,
                                        @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        poseStack.pushPose();
        if (offset != null) poseStack.translate(offset.x(), offset.y(), offset.z());
        if (rotation != null) poseStack.mulPose(rotation);

        double radiusInner = radius - width / 2;
        double radiusOuter = radius + width / 2;
        double length = colors.get(colors.size() - 1).angle - colors.get(0).angle;
        int segmentCount = (int) (Math.toDegrees(length) / step);

        int index = 0;
        List<AngleInfo> angleInfos = new ArrayList<>();
        for (int i = 0; i < segmentCount; i++) {
            double angle = colors.get(0).angle + length * i / segmentCount;
            if (index + 1 < colors.size() && colors.get(index + 1).angle <= angle) index++;
            AngleInfo pre = colors.get(index);
            AngleInfo post = colors.get(index + 1);
            Color color = lerp(new Color(pre.color, true), new Color(post.color, true), (angle - pre.angle) / (post.angle - pre.angle));
            angleInfos.add(new AngleInfo(angle, color.getRGB()));
        }

        for (int i = 0; i < angleInfos.size() - 1; i++) {
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusInner, (float) radiusOuter, 0, 0);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusOuter, (float) radius, 0, (float) height);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radius, (float) radiusInner, (float) height, 0);
        }

        poseStack.popPose();
    }

    /**
     * 渲染一个绕Y轴旋转的完整多色光环,颜色关键点间会使用插值过渡
     *
     * @param poseStack PoseStack
     * @param consumer  Consumer
     * @param gameTime  游戏总刻数
     * @param speed     旋转速度(角度/刻)
     * @param radius    半径
     * @param width     水平厚度
     * @param height    垂直厚度
     * @param step      采样步长(角度)
     * @param colors    所有颜色关键点
     * @param clockwise 顺/逆时针旋转
     * @param offset    坐标变换
     * @param rotation  旋转变换
     */
    public static void render(PoseStack poseStack, VertexConsumer consumer, long gameTime, double speed,
                              double radius, double width, double height, double step, List<AngleInfo> colors,
                              boolean clockwise, @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        double cycle = 360 / speed;
        double progress = gameTime % cycle / cycle;
        Quaternionf quaternionf = clockwise ? Axis.YP.rotation((float) (2 * Math.PI * progress)) : Axis.YN.rotation((float) (2 * Math.PI * progress));
        if (rotation != null) quaternionf = rotation.mul(quaternionf);
        renderStatic(poseStack, consumer, radius, width, height, step, colors, offset, quaternionf);
    }

    /**
     * 渲染一个静止的完整多色光环,颜色关键点间会使用插值过渡
     *
     * @param poseStack PoseStack
     * @param consumer  Consumer
     * @param radius    半径
     * @param width     水平厚度
     * @param height    垂直厚度
     * @param step      采样步长(角度)
     * @param colors    所有颜色关键点
     * @param offset    坐标变换
     * @param rotation  旋转变换
     */
    public static void renderStatic(PoseStack poseStack, VertexConsumer consumer, double radius,
                                    double width, double height, double step, List<AngleInfo> colors,
                                    @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        poseStack.pushPose();
        if (offset != null) poseStack.translate(offset.x(), offset.y(), offset.z());
        if (rotation != null) poseStack.mulPose(rotation);

        double radiusInner = radius - width / 2;
        double radiusOuter = radius + width / 2;
        int segmentCount = (int) (360 / step);

        int index = 0;
        List<AngleInfo> angleInfos = new ArrayList<>();
        for (int i = 0; i < segmentCount; i++) {
            double angle = 2D * Math.PI * i / segmentCount;
            if (index + 1 < colors.size() && colors.get(index + 1).angle <= angle) index++;
            AngleInfo pre = colors.get(index);
            AngleInfo post = index == colors.size() - 1 ? new AngleInfo(2D * Math.PI, colors.get(0).color) : colors.get(index + 1);
            Color color = lerp(new Color(pre.color, true), new Color(post.color, true), (angle - pre.angle) / (post.angle - pre.angle));
            angleInfos.add(new AngleInfo(angle, color.getRGB()));
        }

        for (int i = 0; i < angleInfos.size() - 1; i++) {
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusInner, (float) radiusOuter, 0, 0);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusOuter, (float) radius, 0, (float) height);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radius, (float) radiusInner, (float) height, 0);
        }
        segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radiusInner, (float) radiusOuter, 0, 0);
        segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radiusOuter, (float) radius, 0, (float) height);
        segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radius, (float) radiusInner, (float) height, 0);

        poseStack.popPose();
    }

    /**
     * 渲染一个静止的完整纯色光环
     *
     * @param poseStack PoseStack
     * @param consumer  Consumer
     * @param radius    半径
     * @param width     水平厚度
     * @param height    垂直厚度
     * @param step      采样步长(角度)
     * @param color     颜色
     * @param offset    坐标变换
     * @param rotation  旋转变换
     */
    public static void renderStatic(PoseStack poseStack, VertexConsumer consumer, double radius,
                                    double width, double height, double step, Color color,
                                    @Nullable Vector3f offset, @Nullable Quaternionf rotation) {
        poseStack.pushPose();
        if (offset != null) poseStack.translate(offset.x(), offset.y(), offset.z());
        if (rotation != null) poseStack.mulPose(rotation);

        double radiusInner = radius - width / 2;
        double radiusOuter = radius + width / 2;
        int segmentCount = (int) (360 / step);

        List<AngleInfo> angleInfos = new ArrayList<>();
        for (int i = 0; i < segmentCount; i++) {
            double angle = 2D * Math.PI * i / segmentCount;
            angleInfos.add(new AngleInfo(angle, color.getRGB()));
        }

        for (int i = 0; i < angleInfos.size() - 1; i++) {
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusInner, (float) radiusOuter, 0, 0);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radiusOuter, (float) radius, 0, (float) height);
            segment(consumer, poseStack.last(), angleInfos.get(i), angleInfos.get(i + 1), (float) radius, (float) radiusInner, (float) height, 0);
        }
        segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radiusInner, (float) radiusOuter, 0, 0);
        segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radiusOuter, (float) radius, 0, (float) height);
        segment(consumer, poseStack.last(), angleInfos.get(angleInfos.size() - 1), angleInfos.get(0), (float) radius, (float) radiusInner, (float) height, 0);

        poseStack.popPose();
    }

    private static void segment(VertexConsumer consumer, PoseStack.Pose pose, AngleInfo pre, AngleInfo post, float radius, float radius1, float y, float y1) {
        pre.vertex(consumer, pose, radius, y);
        pre.vertex(consumer, pose, radius1, y1);
        post.vertex(consumer, pose, radius1, y1);
        post.vertex(consumer, pose, radius, y);
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private static Color lerp(Color from, Color to, double amount) {
        return new Color(
                Mth.clamp((int) lerp(from.getRed(), to.getRed(), amount), 0, 255),
                Mth.clamp((int) lerp(from.getGreen(), to.getGreen(), amount), 0, 255),
                Mth.clamp((int) lerp(from.getBlue(), to.getBlue(), amount), 0, 255),
                Mth.clamp((int) lerp(from.getAlpha(), to.getAlpha(), amount), 0, 255)
        );
    }

    public static class AngleInfo {
        private final float sin;
        private final float cos;
        private final int color;
        public final double angle;

        public AngleInfo(double angle, int color) {
            this.sin = (float) Math.sin(angle);
            this.cos = (float) Math.cos(angle);
            this.color = color;
            this.angle = angle;
        }

        public void vertex(VertexConsumer consumer, PoseStack.Pose pose, float radius) {
            vertex(consumer, pose, radius, 0);
        }

        public void vertex(VertexConsumer consumer, PoseStack.Pose pose, float radius, float y) {
            Vector4f pos = new Vector4f(cos * radius, y, sin * radius, 1);
            pos.mul(pose.pose());
            consumer
                    .vertex(pos.x(), pos.y(), pos.z())
                    .color(this.color)
                    .uv(0.0f, 0.0f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .uv2(0xF000F0)
                    .normal(0.0f, 1.0f, 0.0f)
                    .endVertex();
        }
    }
}
