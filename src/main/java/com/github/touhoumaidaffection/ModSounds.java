package com.github.touhoumaidaffection;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final SoundEvent KISS = Registry.register(BuiltInRegistries.SOUND_EVENT, new ResourceLocation(TouhouMaidAffection.MOD_ID, "kiss"),
            SoundEvent.createVariableRangeEvent(
                    new ResourceLocation(TouhouMaidAffection.MOD_ID, "touhou_maid_affection.kiss")
            ));

    public static void init() {

    }
}
