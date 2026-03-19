package com.mastermarisa.maidbeacon;

import cn.sh1rocu.tlmo_additions.api.event.EntityLeaveLevelEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.EntityJoinLevelEvent;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.event.MaidTracker;
import com.mastermarisa.maidbeacon.event.OnInteractMaid;
import com.mastermarisa.maidbeacon.init.ModCompats;
import com.mastermarisa.maidbeacon.init.ModDataComponents;
import com.mastermarisa.maidbeacon.init.ModEntities;
import com.mastermarisa.maidbeacon.init.ModItems;
import com.mastermarisa.maidbeacon.init.registry.CommonRegistry;
import com.mastermarisa.maidbeacon.network.NetworkHandler;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;

public class MaidBeacon {
    public static final String MOD_ID = "maidbeacon";

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init() {
        Config.register();
        NetworkHandler.registerC2SMessages();
        ModEntities.init();
        ModItems.init();
        ModDataComponents.init();
        ModCompats.register();

        subscribeEvents();
    }

    private static void subscribeEvents() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(CommonRegistry::addItemsToCreativeTab);
        EntityJoinLevelEvent.CALLBACK.register(MaidTracker::onEntityJoin);
        EntityLeaveLevelEvent.EVENT.register(MaidTracker::onEntityLeave);
        UseEntityCallback.EVENT.register(OnInteractMaid::onPlayerInteractMaidEvent);
    }
}
