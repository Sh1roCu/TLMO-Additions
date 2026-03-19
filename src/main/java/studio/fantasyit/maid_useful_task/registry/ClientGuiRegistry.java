package studio.fantasyit.maid_useful_task.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import studio.fantasyit.maid_useful_task.menu.MaidLoggingConfigGui;
import studio.fantasyit.maid_useful_task.menu.MaidReviveConfigGui;

@Environment(EnvType.CLIENT)
public class ClientGuiRegistry {
    public static void init() {
        MenuScreens.register(GuiRegistry.MAID_LOGGING_CONFIG_GUI, MaidLoggingConfigGui::new);
        MenuScreens.register(GuiRegistry.MAID_REVIVE_CONFIG_GUI, MaidReviveConfigGui::new);
    }
}
