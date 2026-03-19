package com.mastermarisa.maidbeacon.init;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.entity.ExtraRenderingEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class ModEntities {
    public static final EntityType<ExtraRenderingEntity> EXTRA_RENDERING_ENTITY = register("extra_rendering_entity", ExtraRenderingEntity.TYPE);

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MaidBeacon.MOD_ID, name), type);
    }

    public static void init() {

    }
}
