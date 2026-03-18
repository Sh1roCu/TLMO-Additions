package com.mastermarisa.maidbeacon.data;

import com.github.tartaricacid.touhoulittlemaid.api.entity.data.TaskDataKey;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.init.ModTaskDataKeys;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ExtraRenderSettings {
    public boolean enableRendering = true;
    public boolean enableBeaconBeam = true;
    public boolean enableBeaconModel = true;
    public boolean enableHeadHalo = false;
    public boolean enableSkyHalo = true;
    public byte headHaloStyle = 0;
    public byte skyHaloStyle = 0;

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("enable_rendering", this.enableRendering);
        tag.putBoolean("enable_beacon_beam", this.enableBeaconBeam);
        tag.putBoolean("enable_beacon_model", this.enableBeaconModel);
        tag.putBoolean("enable_head_halo", this.enableHeadHalo);
        tag.putBoolean("enable_sky_halo", this.enableSkyHalo);
        tag.putByte("head_halo_style", this.headHaloStyle);
        tag.putByte("sky_halo_style", this.skyHaloStyle);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("enable_rendering")) {
            this.enableRendering = tag.getBoolean("enable_rendering");
            this.enableBeaconBeam = tag.getBoolean("enable_beacon_beam");
            this.enableBeaconModel = tag.getBoolean("enable_beacon_model");
            this.enableHeadHalo = tag.getBoolean("enable_head_halo");
            this.enableSkyHalo = tag.getBoolean("enable_sky_halo");
            this.headHaloStyle = tag.getByte("head_halo_style");
            this.skyHaloStyle = tag.getByte("sky_halo_style");
        }
    }

    public List<Pair<String, String>> getDisplayPairs() {
        return List.of(
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_rendering").getString(), String.valueOf(enableRendering)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_beacon_beam").getString(), String.valueOf(enableBeaconBeam)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_beacon_model").getString(), String.valueOf(enableBeaconModel)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_head_halo").getString(), String.valueOf(enableHeadHalo)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_sky_halo").getString(), String.valueOf(enableSkyHalo)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.head_halo_style").getString(), String.valueOf(headHaloStyle)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.sky_halo_style").getString(), String.valueOf(skyHaloStyle))
        );
    }

    public static ExtraRenderSettings getOrCreate(EntityMaid maid) {
        ExtraRenderSettings settings = maid.getData(ModTaskDataKeys.EXTRA_RENDER_SETTINGS);
        if (settings == null) {
            settings = new ExtraRenderSettings();
            maid.setAndSyncData(ModTaskDataKeys.EXTRA_RENDER_SETTINGS, settings);
        }
        return settings;
    }

    public static class DATA_KEY implements TaskDataKey<ExtraRenderSettings> {
        private static final ResourceLocation KEY = MaidBeacon.resourceLocation("extra_render_settings");

        @Override
        public ResourceLocation getKey() {
            return KEY;
        }

        @Override
        public CompoundTag writeSaveData(ExtraRenderSettings extraRenderSettings) {
            return extraRenderSettings.serializeNBT();
        }

        @Override
        public ExtraRenderSettings readSaveData(CompoundTag compoundTag) {
            ExtraRenderSettings settings = new ExtraRenderSettings();
            settings.deserializeNBT(compoundTag);
            return settings;
        }
    }
}
