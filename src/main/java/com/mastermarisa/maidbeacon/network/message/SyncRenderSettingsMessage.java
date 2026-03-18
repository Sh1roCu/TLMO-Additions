package com.mastermarisa.maidbeacon.network.message;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.data.ExtraRenderSettings;
import com.mastermarisa.maidbeacon.init.ModTaskDataKeys;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.UUID;

public record SyncRenderSettingsMessage(UUID uuid, CompoundTag compoundTag) {
    public static final ResourceLocation ID = MaidBeacon.resourceLocation("sync_render_settings");

    public static FriendlyByteBuf encode(SyncRenderSettingsMessage message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUUID(message.uuid);
        buf.writeNbt(message.compoundTag);
        return buf;
    }

    public static SyncRenderSettingsMessage decode(FriendlyByteBuf buf) {
        return new SyncRenderSettingsMessage(buf.readUUID(), buf.readNbt());
    }


    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var message = decode(buf);
        server.execute(() -> {
            if (player == null) {
                return;
            }
            ServerLevel level = player.serverLevel();
            if (level.getEntity(message.uuid()) instanceof EntityMaid maid) {
                ExtraRenderSettings settings = new ExtraRenderSettings();
                settings.deserializeNBT(message.compoundTag);
                maid.setAndSyncData(ModTaskDataKeys.EXTRA_RENDER_SETTINGS, settings);
            }
        });
    }
}
