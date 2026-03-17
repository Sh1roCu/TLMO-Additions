package com.sch246.muhc.config;


import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;

public final class ClothConfigIntegration {
    private ClothConfigIntegration() {
    }

    public static Screen registerConfigScreen(Screen parent) {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            // 复用逻辑：创建完整 Screen
            return ClothConfigScreen.create(parent);
        } else {
            // 降级逻辑：提示下载
            return new NoClothConfigScreen(parent);
        }
    }
}
