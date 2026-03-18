package com.mastermarisa.maidbeacon.network.message;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.init.ModItems;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public record OperateEffectMessage(int actionCode, int index, int hand, UUID uuid) {
    public static final ResourceLocation ID = MaidBeacon.resourceLocation("change_aura_state");

    public static FriendlyByteBuf encode(OperateEffectMessage message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(message.actionCode);
        buf.writeInt(message.index);
        buf.writeInt(message.hand);
        buf.writeUUID(message.uuid);
        return buf;
    }

    public static OperateEffectMessage decode(FriendlyByteBuf buf) {
        return new OperateEffectMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readUUID());
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var message = decode(buf);
        server.execute(() -> {
            if (player == null) {
                return;
            }
            ServerLevel level = player.serverLevel();
            if (level.getPlayerByUUID(message.uuid()) instanceof ServerPlayer serverPlayer) {
                ItemStack itemInHand = serverPlayer.getItemInHand(message.hand == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
                if (itemInHand.is(ModItems.MOBILE_BEACON)) {
                    MobileBeaconData mobileBeaconData = MobileBeaconData.getOrDefault(itemInHand);
                    switch (message.actionCode) {
                        case 0 -> {
                            mobileBeaconData.activated.remove(message.index());
                        }
                        case 1 -> {
                            if (mobileBeaconData.getUsedCost() + Config.EFFECT_AURAS().get(message.index).cost <= Config.getCost(mobileBeaconData.getLevel())) {
                                mobileBeaconData.activated.add(message.index());
                            }
                        }
                    }
                    MobileBeaconData.setData(itemInHand, mobileBeaconData);
                }
            }
        });
    }
}