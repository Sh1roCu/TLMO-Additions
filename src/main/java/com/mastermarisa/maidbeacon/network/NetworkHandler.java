package com.mastermarisa.maidbeacon.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkHandler {
    public static void registerC2SMessages() {
        PayloadTypeRegistry.playC2S().register(OperateEffectPayload.TYPE, OperateEffectPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(OperateEffectPayload.TYPE, OperateEffectPayload::handle);

        PayloadTypeRegistry.playC2S().register(UpgradeBeaconPayload.TYPE, UpgradeBeaconPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(UpgradeBeaconPayload.TYPE, UpgradeBeaconPayload::handle);

        PayloadTypeRegistry.playC2S().register(SyncRenderSettingsPayload.TYPE, SyncRenderSettingsPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SyncRenderSettingsPayload.TYPE, SyncRenderSettingsPayload::handle);
    }
}
