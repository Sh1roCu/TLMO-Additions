package com.github.touhoumaidaffection;

import cn.sh1rocu.tlmo_additions.TLMOAdditions;
import cn.sh1rocu.tlmo_additions.api.event.PlayerLoggedOutEvent;
import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.touhoumaidaffection.handler.KissMaidHandler;
import com.github.touhoumaidaffection.network.KissCarryRequestPayload;
import com.github.touhoumaidaffection.network.KissMaidPayload;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TouhouMaidAffection {
    public static final String MOD_ID = "touhou_maid_affection";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        NeoForgeConfigRegistry.INSTANCE.register(TLMOAdditions.MODID, ModConfig.Type.COMMON, com.github.touhoumaidaffection.ModConfig.SPEC, MOD_ID + "-common.toml");

        ModSounds.init();
        ModEffects.init();

        PayloadTypeRegistry.playS2C().register(KissMaidPayload.TYPE, KissMaidPayload.STREAM_CODEC);

        PayloadTypeRegistry.playC2S().register(KissCarryRequestPayload.TYPE, KissCarryRequestPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(KissCarryRequestPayload.TYPE, KissCarryRequestPayload::handle);

        subscribeEvents();

        LOGGER.info("Touhou Maid: Affection loaded! Now you can kiss your maid~");
    }

    private static void subscribeEvents() {
        PlayerLoggedOutEvent.EVENT.register(KissMaidHandler::onPlayerLoggedOut);
        ServerLifecycleEvents.SERVER_STOPPED.register(KissMaidHandler::onServerStopped);
        InteractMaidEvent.CALLBACK.register(KissMaidHandler::onInteractMaid);
    }
}
