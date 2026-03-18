package com.github.touhoumaidaffection.network;

import com.github.touhoumaidaffection.TouhouMaidAffection;
import com.github.touhoumaidaffection.client.KissClientHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class KissMaidPayload {
    public static final ResourceLocation ID = new ResourceLocation(TouhouMaidAffection.MOD_ID, "kiss_maid");

    private final int maidEntityId;
    private final int playerEntityId;
    private final boolean carriedKiss;

    public KissMaidPayload(int maidEntityId, int playerEntityId, boolean carriedKiss) {
        this.maidEntityId = maidEntityId;
        this.playerEntityId = playerEntityId;
        this.carriedKiss = carriedKiss;
    }

    public int maidEntityId() {
        return maidEntityId;
    }

    public int playerEntityId() {
        return playerEntityId;
    }

    public boolean carriedKiss() {
        return carriedKiss;
    }

    public static FriendlyByteBuf encode(KissMaidPayload message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(message.maidEntityId);
        buf.writeVarInt(message.playerEntityId);
        buf.writeBoolean(message.carriedKiss);
        return buf;
    }

    public static KissMaidPayload decode(FriendlyByteBuf buf) {
        return new KissMaidPayload(buf.readVarInt(), buf.readVarInt(), buf.readBoolean());
    }

    @Environment(EnvType.CLIENT)
    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var message = decode(buf);
        client.execute(() -> KissClientHandler.handle(message));
    }
}
