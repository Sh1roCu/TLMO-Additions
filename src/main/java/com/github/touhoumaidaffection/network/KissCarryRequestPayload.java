package com.github.touhoumaidaffection.network;

import com.github.touhoumaidaffection.TouhouMaidAffection;
import com.github.touhoumaidaffection.handler.KissMaidHandler;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class KissCarryRequestPayload implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KissCarryRequestPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TouhouMaidAffection.MOD_ID, "kiss_carry_request"));

    public static final KissCarryRequestPayload DUMMY = new KissCarryRequestPayload();

    public static final StreamCodec<ByteBuf, KissCarryRequestPayload> STREAM_CODEC = StreamCodec.unit(DUMMY);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(KissCarryRequestPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            KissMaidHandler.tryKissCarriedMaid(context.player());
        });
    }
}