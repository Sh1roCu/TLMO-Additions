package com.mastermarisa.maidbeacon.data;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExtraRenderSettings {
    public boolean enableRendering = true;
    public boolean enableBeaconBeam = true;
    public boolean enableBeaconModel = true;
    public boolean enableHeadHalo = false;
    public boolean enableSkyHalo = true;
    public byte headHaloStyle = 0;
    public byte skyHaloStyle = 0;

    public static final Codec<ExtraRenderSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("enable_rendering").forGetter(e -> e.enableRendering),
            Codec.BOOL.fieldOf("enable_beacon_beam").forGetter(e -> e.enableBeaconBeam),
            Codec.BOOL.fieldOf("enable_beacon_model").forGetter(e -> e.enableBeaconModel),
            Codec.BOOL.fieldOf("enable_head_halo").forGetter(e -> e.enableHeadHalo),
            Codec.BOOL.fieldOf("enable_sky_halo").forGetter(e -> e.enableSkyHalo),
            Codec.BYTE.fieldOf("head_halo_style").forGetter(e -> e.headHaloStyle),
            Codec.BYTE.fieldOf("sky_halo_style").forGetter(e -> e.skyHaloStyle)
    ).apply(instance, ExtraRenderSettings::new));

    public ExtraRenderSettings() {
        this(true, true, true, false, true, (byte) 0, (byte) 0);
    }

    public ExtraRenderSettings(boolean enableRendering, boolean enableBeaconBeam, boolean enableBeaconModel, boolean enableHeadHalo, boolean enableSkyHalo, byte headHaloStyle, byte skyHaloStyle) {
        this.enableRendering = enableRendering;
        this.enableBeaconBeam = enableBeaconBeam;
        this.enableBeaconModel = enableBeaconModel;
        this.enableHeadHalo = enableHeadHalo;
        this.enableSkyHalo = enableSkyHalo;
        this.headHaloStyle = headHaloStyle;
        this.skyHaloStyle = skyHaloStyle;
    }

    public CompoundTag serializeNBT(@Nullable HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("enable_rendering", enableRendering);
        tag.putBoolean("enable_beacon_beam", enableBeaconBeam);
        tag.putBoolean("enable_beacon_model", enableBeaconModel);
        tag.putBoolean("enable_head_halo", enableHeadHalo);
        tag.putBoolean("enable_sky_halo", enableSkyHalo);
        tag.putByte("head_halo_style", headHaloStyle);
        tag.putByte("sky_halo_style", skyHaloStyle);
        return tag;
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        if (tag.contains("enable_rendering")) {
            enableRendering = tag.getBoolean("enable_rendering");
            enableBeaconBeam = tag.getBoolean("enable_beacon_beam");
            enableBeaconModel = tag.getBoolean("enable_beacon_model");
            enableHeadHalo = tag.getBoolean("enable_head_halo");
            enableSkyHalo = tag.getBoolean("enable_sky_halo");
            headHaloStyle = tag.getByte("head_halo_style");
            skyHaloStyle = tag.getByte("sky_halo_style");
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

    public static class Syncer implements StreamCodec<RegistryFriendlyByteBuf, ExtraRenderSettings> {
        @Override
        public void encode(RegistryFriendlyByteBuf registryFriendlyByteBuf, ExtraRenderSettings extraRenderSettings) {
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableRendering);
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableBeaconBeam);
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableBeaconModel);
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableHeadHalo);
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableSkyHalo);
            registryFriendlyByteBuf.writeByte(extraRenderSettings.headHaloStyle);
            registryFriendlyByteBuf.writeByte(extraRenderSettings.skyHaloStyle);
        }

        @Override
        public ExtraRenderSettings decode(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
            ExtraRenderSettings extraRenderSettings = new ExtraRenderSettings();
            extraRenderSettings.enableRendering = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.enableBeaconBeam = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.enableBeaconModel = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.enableHeadHalo = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.enableSkyHalo = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.headHaloStyle = registryFriendlyByteBuf.readByte();
            extraRenderSettings.skyHaloStyle = registryFriendlyByteBuf.readByte();
            return extraRenderSettings;
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static final AttachmentType<ExtraRenderSettings> TYPE = AttachmentRegistry.create(ResourceLocation.fromNamespaceAndPath(MaidBeacon.MOD_ID, "extra_render_settings"),
            builder -> builder
                    .initializer(ExtraRenderSettings::new)
                    .persistent(CODEC)
                    .syncWith(new Syncer(), AttachmentSyncPredicate.all())
    );
}