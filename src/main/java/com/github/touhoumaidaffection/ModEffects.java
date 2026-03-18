package com.github.touhoumaidaffection;

import com.github.touhoumaidaffection.effect.MaidsPrayerEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
    public static final MobEffect MAIDS_PRAYER = Registry.register(BuiltInRegistries.MOB_EFFECT,
            new ResourceLocation(TouhouMaidAffection.MOD_ID, "maids_prayer"),
            new MaidsPrayerEffect());

    public static void init() {

    }
}
