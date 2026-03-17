package studio.fantasyit.maid_useful_task.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class Network {
    public static void registerC2SMessages() {
        ServerPlayNetworking.registerGlobalReceiver(MaidAllowHandleVehicle.ID, MaidAllowHandleVehicle::handle);
        ServerPlayNetworking.registerGlobalReceiver(MaidConfigurePacket.ID, MaidConfigurePacket::handle);
    }

    @Environment(EnvType.CLIENT)
    public static void registerS2CMessages() {
        ClientPlayNetworking.registerGlobalReceiver(MaidSyncVehiclePacket.ID, MaidSyncVehiclePacket::handle);
    }

    @Environment(EnvType.CLIENT)
    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }
}
