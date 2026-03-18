package cn.sh1rocu.tlmo_additions.client;

import cn.sh1rocu.touhoulittlemaid.api.event.KeyInputCallback;
import com.mastermarisa.maidbeacon.client.event.OnClientSetup;
import net.fabricmc.api.ClientModInitializer;
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
    }
}
