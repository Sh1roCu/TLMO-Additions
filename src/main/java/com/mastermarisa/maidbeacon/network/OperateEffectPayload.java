package com.mastermarisa.maidbeacon.network;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.init.ModDataComponents;
import com.mastermarisa.maidbeacon.init.ModItems;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record OperateEffectPayload(int actionCode, int index, int hand, UUID uuid) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OperateEffectPayload> TYPE =
            new CustomPacketPayload.Type<>(MaidBeacon.resourceLocation("change_aura_state"));

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, OperateEffectPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    OperateEffectPayload::actionCode,
                    ByteBufCodecs.INT,
                    OperateEffectPayload::index,
                    ByteBufCodecs.INT,
                    OperateEffectPayload::hand,
                    UUIDUtil.STREAM_CODEC,
                    OperateEffectPayload::uuid,
                    OperateEffectPayload::new
            );

    public static void handle(OperateEffectPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerLevel level = context.player().serverLevel();
            ServerPlayer player = (ServerPlayer) level.getPlayerByUUID(payload.uuid());
            if (player != null) {
                ItemStack itemInHand = player.getItemInHand(payload.hand == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
                if (itemInHand.is(ModItems.MOBILE_BEACON)) {
                    MobileBeaconData mobileBeaconData = itemInHand.getOrDefault(ModDataComponents.MOBILE_BEACON_DATA, new MobileBeaconData());
                    switch (payload.actionCode) {
                        case 0 -> {
                            mobileBeaconData.activated.remove(payload.index());
                        }
                        case 1 -> {
                            if (mobileBeaconData.getUsedCost() + Config.EFFECT_AURAS().get(payload.index).cost <= Config.getCost(mobileBeaconData.getLevel())) {
                                mobileBeaconData.activated.add(payload.index());
                            }
                        }
                    }
                    itemInHand.set(ModDataComponents.MOBILE_BEACON_DATA, mobileBeaconData);
                }
            }
        });
    }
}