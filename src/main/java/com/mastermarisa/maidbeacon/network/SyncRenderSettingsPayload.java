package com.mastermarisa.maidbeacon.network;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.data.ExtraRenderSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record SyncRenderSettingsPayload(UUID uuid, CompoundTag compoundTag) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncRenderSettingsPayload> TYPE =
            new CustomPacketPayload.Type<>(MaidBeacon.resourceLocation("sync_render_settings"));

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, SyncRenderSettingsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    UUIDUtil.STREAM_CODEC,
                    SyncRenderSettingsPayload::uuid,
                    ByteBufCodecs.COMPOUND_TAG,
                    SyncRenderSettingsPayload::compoundTag,
                    SyncRenderSettingsPayload::new
            );

    @SuppressWarnings("UnstableApiUsage")
    public static void handle(SyncRenderSettingsPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerLevel level = context.player().serverLevel();
            if (level.getEntity(payload.uuid()) instanceof EntityMaid maid) {
                ExtraRenderSettings settings = new ExtraRenderSettings();
                settings.deserializeNBT(level.registryAccess(), payload.compoundTag());
                maid.setAttached(ExtraRenderSettings.TYPE, settings);
            }
        });
    }
}