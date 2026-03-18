package com.mastermarisa.maidbeacon.config;

import cn.sh1rocu.tlmo_additions.TLMOAdditions;
import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.data.EffectAura;
import com.mastermarisa.maidbeacon.utils.EncodeUtils;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Config {
    private static List<EffectAura> EFFECT_AURAS;
    private static ConcurrentHashMap<Integer, List<EffectAura>> MAP_BY_LEVEL;
    private static List<ItemStack> ITEMSTACK_TO_UPGRADE_BEACON_LEVEL;

    public static Integer CHECK_INTERVAL() {
        return Server.EFFECT_CHECK_INTERVAL.get();
    }

    public static List<? extends String> WHITELIST() {
        return Server.TARGET_WHITELIST.get();
    }

    public static List<EffectAura> EFFECT_AURAS() {
        if (EFFECT_AURAS == null)
            EFFECT_AURAS = Server.EFFECT_AURAS.get().stream().map(s -> EncodeUtils.fromJson(s, EffectAura.class)).toList();
        return EFFECT_AURAS;
    }

    public static List<ItemStack> ITEMSTACK_TO_UPGRADE_BEACON_LEVEL() {
        if (ITEMSTACK_TO_UPGRADE_BEACON_LEVEL == null)
            ITEMSTACK_TO_UPGRADE_BEACON_LEVEL = Server.ITEMSTACK_TO_UPGRADE_BEACON_LEVEL.get().stream()
                    .map(j -> EncodeUtils.fromJson(j, Server.ItemStackData.class))
                    .map(d -> new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(d.id)), d.count)).toList();

        return ITEMSTACK_TO_UPGRADE_BEACON_LEVEL;
    }

    public static int MAX_LEVEL() {
        return EFFECT_AURAS().stream().map(e -> e.beaconLevel).max(Comparator.comparingInt(Integer::intValue)).orElse(0);
    }

    public static List<EffectAura> BY_LEVEL(int level) {
        if (MAP_BY_LEVEL == null) {
            MAP_BY_LEVEL = new ConcurrentHashMap<>();
            for (EffectAura aura : EFFECT_AURAS()) {
                MAP_BY_LEVEL.computeIfAbsent(aura.beaconLevel, (i) -> new ArrayList<>());
                MAP_BY_LEVEL.get(aura.beaconLevel).add(aura);
            }
        }
        return MAP_BY_LEVEL.getOrDefault(level, null);
    }

    public static Integer getCost(int level) {
        List<? extends Integer> costs = Server.BEACON_COST.get();
        if (level >= costs.size()) return costs.get(costs.size() - 1);
        return costs.get(level);
    }

    public static boolean ENABLE_BEACON_RANGE_RENDERING() {
        return Server.ENABLE_BEACON_RANGE_RENDERING.get();
    }

    private static class Client {
        public static final ForgeConfigSpec.Builder BUILDER;
        public static final ForgeConfigSpec SPEC;

        public static void register() {
            ForgeConfigRegistry.INSTANCE.register(TLMOAdditions.MODID, ModConfig.Type.CLIENT, SPEC, MaidBeacon.MOD_ID + "-client.toml");
        }

        static {
            BUILDER = new ForgeConfigSpec.Builder();

            SPEC = BUILDER.build();
        }
    }

    private static class Server {
        public static final List<String> defaultAuraData;

        public static final ForgeConfigSpec.Builder BUILDER;
        public static final ForgeConfigSpec SPEC;
        public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EFFECT_AURAS;
        public static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> BEACON_COST;
        public static final ForgeConfigSpec.ConfigValue<List<? extends String>> TARGET_WHITELIST;
        public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEMSTACK_TO_UPGRADE_BEACON_LEVEL;
        public static final ForgeConfigSpec.IntValue EFFECT_CHECK_INTERVAL;
        public static final ForgeConfigSpec.BooleanValue ENABLE_BEACON_RANGE_RENDERING;

        public static void register() {
            ForgeConfigRegistry.INSTANCE.register(TLMOAdditions.MODID, ModConfig.Type.SERVER, SPEC, MaidBeacon.MOD_ID + "-server.toml");
        }

        static {
            defaultAuraData = Stream.of(
                    new EffectAura("minecraft:speed", 0, 0, 1, 20),
                    new EffectAura("minecraft:haste", 0, 0, 1, 20),
                    new EffectAura("minecraft:resistance", 0, 1, 1, 20),
                    new EffectAura("minecraft:jump_boost", 0, 1, 1, 20),
                    new EffectAura("minecraft:strength", 0, 2, 1, 20),
                    new EffectAura("minecraft:regeneration", 0, 3, 2, 20)
            ).map(EncodeUtils::toJson).toList();

            BUILDER = new ForgeConfigSpec.Builder();

            EFFECT_AURAS = BUILDER
                    .translation("config.maidbeacon.server.effect_auras")
                    .defineList("effect_auras", defaultAuraData, (e) -> e instanceof String);

            BEACON_COST = BUILDER
                    .translation("config.maidbeacon.server.beacon_cost")
                    .defineList("beacon_cost", List.of(1, 2, 3, 4), (e) -> e instanceof Integer);

            TARGET_WHITELIST = BUILDER
                    .translation("config.maidbeacon.server.whitelist")
                    .defineList("target_whitelist", List.of(
                            BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PLAYER).toString(),
                            "touhou_little_maid:maid"
                    ), (s) -> s instanceof String);

            ITEMSTACK_TO_UPGRADE_BEACON_LEVEL = BUILDER
                    .translation("config.maidbeacon.server.itemstack")
                    .defineList("itemstack_to_upgrade_beacon_level", List.of(
                            EncodeUtils.toJson(new ItemStackData("minecraft:gold_block", 9)),
                            EncodeUtils.toJson(new ItemStackData("minecraft:diamond_block", 9)),
                            EncodeUtils.toJson(new ItemStackData("minecraft:netherite_block", 9))
                    ), (e) -> e instanceof String);

            EFFECT_CHECK_INTERVAL = BUILDER
                    .translation("config.maidbeacon.server.effect_check_interval")
                    .defineInRange("effect_check_interval", 20, 1, Integer.MAX_VALUE);

            ENABLE_BEACON_RANGE_RENDERING = BUILDER
                    .translation("config.maidbeacon.server.enable_beacon_range_rendering")
                    .define("enable_beacon_range_rendering", false);

            SPEC = BUILDER.build();
        }

        public static class ItemStackData {
            public String id;
            public int count;

            public ItemStackData(String id, int count) {
                this.id = id;
                this.count = count;
            }
        }
    }

    public static void register() {
        Client.register();
        Server.register();
    }
}
