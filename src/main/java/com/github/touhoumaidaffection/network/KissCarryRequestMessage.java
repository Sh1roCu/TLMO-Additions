package com.github.touhoumaidaffection.network;

import com.github.touhoumaidaffection.TouhouMaidAffection;
import com.github.touhoumaidaffection.handler.KissMaidHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class KissCarryRequestMessage {
    public static final ResourceLocation ID = new ResourceLocation(TouhouMaidAffection.MOD_ID, "kiss_carry_request");
    public static final FriendlyByteBuf DUMMY = PacketByteBufs.empty();

    public static void handle(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (sender != null) {
                KissMaidHandler.tryKissCarriedMaid(sender);
            }
        });
    }
}
