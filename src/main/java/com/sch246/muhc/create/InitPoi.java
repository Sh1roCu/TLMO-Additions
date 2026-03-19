package com.sch246.muhc.create;

import com.sch246.muhc.MaidUseHandCrank;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class InitPoi {
    // TODO: wait official Create-Fabric-1.21
    // public static final PoiType HAND_CRANK = register("hand_crank", PoiManager.getCrankPoiType());

    private static PoiType register(String name, PoiType poiType) {
        return PointOfInterestHelper.register(ResourceLocation.fromNamespaceAndPath(MaidUseHandCrank.MODID, name),
                poiType.maxTickets(), poiType.validRange(), poiType.matchingStates());
    }

    public static void init() {

    }
}