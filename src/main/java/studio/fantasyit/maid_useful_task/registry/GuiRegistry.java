package studio.fantasyit.maid_useful_task.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import studio.fantasyit.maid_useful_task.MaidUsefulTask;
import studio.fantasyit.maid_useful_task.menu.MaidLoggingConfigGui;
import studio.fantasyit.maid_useful_task.menu.MaidReviveConfigGui;

public class GuiRegistry {
    public static final MenuType<MaidLoggingConfigGui.Container> MAID_LOGGING_CONFIG_GUI = register("maid_logging_config_gui",
            new ExtendedScreenHandlerType<>(MaidLoggingConfigGui.Container::new, ByteBufCodecs.INT));
    public static final MenuType<MaidReviveConfigGui.Container> MAID_REVIVE_CONFIG_GUI = register("maid_revive_config_gui",
            new ExtendedScreenHandlerType<>(MaidReviveConfigGui.Container::new, ByteBufCodecs.INT));

    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType<T> menuType) {
        return Registry.register(BuiltInRegistries.MENU, ResourceLocation.fromNamespaceAndPath(MaidUsefulTask.MODID, name), menuType);
    }

    public static void init() {

    }
}