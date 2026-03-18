package cn.sh1rocu.tlmo_additions.client;

import cn.sh1rocu.touhoulittlemaid.api.event.KeyInputCallback;
import com.github.touhoumaidaffection.client.*;
import com.github.touhoumaidaffection.network.KissMaidPayload;
import com.mastermarisa.maidbeacon.client.event.OnClientSetup;
import io.github.fabricators_of_create.porting_lib.event.client.CameraSetupCallback;
import io.github.fabricators_of_create.porting_lib.event.client.FieldOfViewEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import studio.fantasyit.maid_useful_task.client.KeyEvents;
import studio.fantasyit.maid_useful_task.client.KeyMapping;
import studio.fantasyit.maid_useful_task.network.Network;
import studio.fantasyit.maid_useful_task.registry.ClientGuiRegistry;

public class TLMOAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Maid Useful Task
        Network.registerS2CMessages();
        KeyMapping.init();
        KeyInputCallback.EVENT.register(KeyEvents::keyInput);
        ClientGuiRegistry.init();

        // Touhou Little Maid: Beacon
        OnClientSetup.registerRenderers();

        // Touhou Maid: Affection
        ClientPlayNetworking.registerGlobalReceiver(KissMaidPayload.ID, KissMaidPayload::handle);
        KissKeyRegisterHandler.onRegisterKeyMappings();
        FieldOfViewEvents.MODIFY.register(KissFovHandler::onComputeFovModifier);
        CameraSetupCallback.EVENT.register(KissFovHandler::onCameraAngles);
        ClientTickEvents.END_CLIENT_TICK.register(KissKeyInputHandler::onClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(KissParticleEffectManager::onClientTick);
    }
}
