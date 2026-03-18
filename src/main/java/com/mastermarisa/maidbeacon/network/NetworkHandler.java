package com.mastermarisa.maidbeacon.network;

import com.mastermarisa.maidbeacon.network.message.OperateEffectMessage;
import com.mastermarisa.maidbeacon.network.message.SyncRenderSettingsMessage;
import com.mastermarisa.maidbeacon.network.message.UpgradeBeaconMessage;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkHandler {
    public static void registerC2SMessages() {
        ServerPlayNetworking.registerGlobalReceiver(OperateEffectMessage.ID, OperateEffectMessage::handle);
        ServerPlayNetworking.registerGlobalReceiver(UpgradeBeaconMessage.ID, UpgradeBeaconMessage::handle);
        ServerPlayNetworking.registerGlobalReceiver(SyncRenderSettingsMessage.ID, SyncRenderSettingsMessage::handle);
    }
}
