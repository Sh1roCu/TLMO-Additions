package com.mastermarisa.maidbeacon.client.event;

import com.mastermarisa.maidbeacon.client.render.MaidExtraRenderer;
import com.mastermarisa.maidbeacon.init.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class OnClientSetup {
    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.EXTRA_RENDERING_ENTITY, MaidExtraRenderer::new);
    }
}
