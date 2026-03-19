package com.mastermarisa.maidbeacon.init;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ModDataComponents {
    public static final DataComponentType<MobileBeaconData> MOBILE_BEACON_DATA;

    public static void init() {

    }

    static {
        MOBILE_BEACON_DATA = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
                ResourceLocation.fromNamespaceAndPath(MaidBeacon.MOD_ID, "mobile_beacon_data"),
                DataComponentType.<MobileBeaconData>builder().persistent(MobileBeaconData.CODEC).networkSynchronized(MobileBeaconData.STREAM_CODEC).build());
    }
}