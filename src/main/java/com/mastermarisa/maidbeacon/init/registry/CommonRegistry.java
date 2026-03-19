package com.mastermarisa.maidbeacon.init.registry;

import com.github.tartaricacid.touhoulittlemaid.init.InitCreativeTabs;
import com.mastermarisa.maidbeacon.init.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.world.item.CreativeModeTab;

public final class CommonRegistry {
    public static void addItemsToCreativeTab(CreativeModeTab group, FabricItemGroupEntries entries) {
        if (group == InitCreativeTabs.MAIN_TAB) {
            entries.accept(ModItems.MOBILE_BEACON);
        }
    }
}
