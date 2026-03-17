package com.sch246.muhc.mixin.accessor;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KineticBlockEntity.class)
public interface KineticBlockEntityAccessor {
    @Accessor("lastCapacityProvided")
    void muhc$setLastCapacityProvided(float lastCapacityProvided);
}
