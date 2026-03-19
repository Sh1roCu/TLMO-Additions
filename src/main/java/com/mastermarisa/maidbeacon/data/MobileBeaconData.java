package com.mastermarisa.maidbeacon.data;

import com.mastermarisa.maidbeacon.config.Config;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static final Codec<MobileBeaconData> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.INT.fieldOf("level").forGetter(MobileBeaconData::getLevel),
                    Codec.INT_STREAM.fieldOf("activated").forGetter(MobileBeaconData::toStream)
            ).apply(instance, MobileBeaconData::new)
    );

    public static final StreamCodec<ByteBuf, MobileBeaconData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
}