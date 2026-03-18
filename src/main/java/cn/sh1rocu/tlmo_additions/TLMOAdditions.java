package cn.sh1rocu.tlmo_additions;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.sch246.muhc.MaidUseHandCrank;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import studio.fantasyit.maid_useful_task.MaidUsefulTask;

public class TLMOAdditions implements ModInitializer {
    public static final String MODID = "tlmo_additions";

    @Override
    public void onInitialize() {
        // Maid Useful Task
        MaidUsefulTask.init();

        // MaidUseHandCrack
        if (FabricLoader.getInstance().isModLoaded("create")) MaidUseHandCrank.init();

        // Touhou Little Maid: Beacon
        MaidBeacon.init();
    }

}
