package com.mastermarisa.maidbeacon.data;

import com.mastermarisa.maidbeacon.config.Config;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class MobileBeaconData {
    private int level;
    public IntSet activated;

    public MobileBeaconData() {
        this.level = 0;
        this.activated = new IntArraySet();
    }

    public MobileBeaconData(int level, IntStream stream) {
        this.level = level;
        this.activated = new IntArraySet(stream.toArray());
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public IntStream toStream() {
        return activated.intStream();
    }

    public int getUsedCost() {
        int total = 0;
        for (Integer i : activated) {
            if (i >= Config.EFFECT_AURAS().size()) continue;
            total += Config.EFFECT_AURAS().get(i).cost;
        }
        return total;
    }

    public ConcurrentHashMap<Float, List<EffectAura>> batched() {
        ConcurrentHashMap<Float, List<EffectAura>> map = new ConcurrentHashMap<>();
        for (int i : activated) {
            if (i >= Config.EFFECT_AURAS().size()) continue;
            EffectAura aura = Config.EFFECT_AURAS().get(i);
            map.computeIfAbsent(aura.range, (f) -> new ArrayList<>(2));
            map.get(aura.range).add(aura);
        }
        return map;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", this.level);
        tag.putIntArray("activated", this.activated.toIntArray());
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        if (!tag.contains("level")) {
            return;
        }
        this.level = tag.getInt("level");
        this.activated = new IntArraySet(tag.getIntArray("activated"));
    }

    public static MobileBeaconData getOrDefault(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        MobileBeaconData mobileBeaconData = new MobileBeaconData();
        if (tag.contains("mobile_beacon_data")) {
            mobileBeaconData.deserializeNBT(tag.getCompound("mobile_beacon_data"));
        }
        return mobileBeaconData;
    }

    public static void setData(ItemStack itemStack, MobileBeaconData mobileBeaconData) {
        itemStack.getOrCreateTag().put("mobile_beacon_data", mobileBeaconData.serializeNBT());
    }

}
