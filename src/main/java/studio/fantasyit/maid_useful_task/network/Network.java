package studio.fantasyit.maid_useful_task.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class Network {
    public static void registerC2SMessages() {
        PayloadTypeRegistry.playC2S().register(MaidAllowHandleVehicle.TYPE, MaidAllowHandleVehicle.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MaidAllowHandleVehicle.TYPE, MaidAllowHandleVehicle::handle);

        PayloadTypeRegistry.playC2S().register(MaidConfigurePacket.TYPE, MaidConfigurePacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MaidConfigurePacket.TYPE, MaidConfigurePacket::handle);
    }

    public static void registerS2CMessages() {
        PayloadTypeRegistry.playS2C().register(MaidSyncVehiclePacket.TYPE, MaidSyncVehiclePacket.STREAM_CODEC);
    }

    @Environment(EnvType.CLIENT)
    public static void registerS2CReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(MaidSyncVehiclePacket.TYPE, MaidSyncVehiclePacket::handle);
    }
}
