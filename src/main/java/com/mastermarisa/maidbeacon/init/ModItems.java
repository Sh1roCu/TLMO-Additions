package com.mastermarisa.maidbeacon.init;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.item.MobileBeaconItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final Item MOBILE_BEACON;

    public static void init() {

    }

    static {
        MOBILE_BEACON = registerItem("mobile_beacon", new MobileBeaconItem());
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MaidBeacon.MOD_ID, name), item);
    }
}
