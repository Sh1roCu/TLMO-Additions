package com.github.touhoumaidaffection.client;

import com.github.touhoumaidaffection.network.KissCarryRequestMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class KissKeyInputHandler {
    public static void onClientTick(Minecraft client) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.isPaused()) {
            return;
        }

        while (KissKeyMappings.KISS_CARRIED.consumeClick()) {
            ClientPlayNetworking.send(KissCarryRequestMessage.ID, KissCarryRequestMessage.DUMMY);
        }
    }
}
