package com.github.touhoumaidaffection.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

@Environment(EnvType.CLIENT)
public class KissKeyRegisterHandler {
    public static void onRegisterKeyMappings() {
        KeyBindingHelper.registerKeyBinding(KissKeyMappings.KISS_CARRIED);
    }
}
