package com.mastermarisa.maidbeacon.item.bauble;

import com.github.tartaricacid.touhoulittlemaid.api.bauble.IMaidBauble;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.BaubleItemHandler;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.data.EffectAura;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.utils.EntityFilter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BeaconEffectGeneratorBauble implements IMaidBauble {
    @Override
    public void onTick(EntityMaid maid, ItemStack baubleItem) {
        Level level = maid.level();
        if (level.isClientSide() || level.getGameTime() % Config.CHECK_INTERVAL() != 0) return;
        MobileBeaconData beaconData = MobileBeaconData.getOrDefault(baubleItem);
        ConcurrentHashMap<Float, List<EffectAura>> batched = beaconData.batched();
        for (float range : batched.keySet()) {
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, maid.getBoundingBox().inflate(range));
            targets.stream().filter(e -> EntityFilter.filter(e.getType()) && e.distanceToSqr(maid) <= Math.pow(range, 2.0D)).forEach(e -> {
                for (EffectAura aura : batched.get(range)) {
                    e.addEffect(new MobEffectInstance(
                            aura.getEffect(),
                            Math.min(Config.CHECK_INTERVAL() + 10, 60),
                            aura.effectLevel,
                            false,
                            true
                    ));
                }
            });
        }
    }

    @Override
    public void onPutOn(EntityMaid maid, ItemStack baubleItem) {
        BaubleItemHandler handler = maid.getMaidBauble();
        for (int i = 0; i < handler.getSlots(); i++) {
            if (handler.getBaubleInSlot(i) instanceof BeaconEffectGeneratorBauble && !handler.getStackInSlot(i).equals(baubleItem)) {
                ItemStack toDrop = handler.extractItem(i, 1, false);
                ItemEntity entity = new ItemEntity(maid.level(), maid.getX(), maid.getY(), maid.getZ(), toDrop);
                entity.setDefaultPickUpDelay();
                maid.level().addFreshEntity(entity);
            }
        }
    }

    @Override
    public boolean syncClient(EntityMaid maid, ItemStack baubleItem) {
        return true;
    }
}
