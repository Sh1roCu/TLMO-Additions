package studio.fantasyit.maid_useful_task;

import cn.sh1rocu.tlmo_additions.TLMOAdditions;
import cn.sh1rocu.tlmo_additions.api.event.PlayerLoggedOutEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.PlayerLoggedInEvent;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import studio.fantasyit.maid_useful_task.event.MaidTickEvent;
import studio.fantasyit.maid_useful_task.event.PlayerEvents;
import studio.fantasyit.maid_useful_task.network.Network;
import studio.fantasyit.maid_useful_task.registry.GuiRegistry;
import studio.fantasyit.maid_useful_task.registry.MemoryModuleRegistry;
import studio.fantasyit.maid_useful_task.vehicle.MaidVehicleManager;

public class MaidUsefulTask {
    public static final Logger logger = LogUtils.getLogger();
    public static final String MODID = "maid_useful_task";

    public static void init() {
        ModConfigEvents.loading(TLMOAdditions.MODID).register(Config::onLoad);
        ForgeConfigRegistry.INSTANCE.register(TLMOAdditions.MODID, ModConfig.Type.COMMON, Config.SPEC, MODID + "-common.toml");
        Network.registerC2SMessages();
        MemoryModuleRegistry.init();
        GuiRegistry.init();
        MaidVehicleManager.register();

        subscribeEvents();
    }

    private static void subscribeEvents() {
        com.github.tartaricacid.touhoulittlemaid.api.event.MaidTickEvent.CALLBACK.register(MaidTickEvent::onTick);
        PlayerLoggedInEvent.CALLBACK.register(PlayerEvents::onPlayerEnter);
        PlayerLoggedOutEvent.EVENT.register(PlayerEvents::onPlayerLeave);
    }
}
