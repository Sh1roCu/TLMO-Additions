package studio.fantasyit.maid_useful_task.compat;

import net.fabricmc.loader.api.FabricLoader;

public class PlayerRevive {
    public static boolean isEnable() {
        return FabricLoader.getInstance().isModLoaded("playerrevive");
    }
}
