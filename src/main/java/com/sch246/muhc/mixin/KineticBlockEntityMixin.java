package com.sch246.muhc.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.sch246.muhc.util.IMaidHandCrank;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KineticBlockEntity.class)
public abstract class KineticBlockEntityMixin {
    @Shadow(remap = false)
    protected float lastCapacityProvided;

    @WrapMethod(remap = false, method = "calculateAddedStressCapacity")
    private float override$calculateAddedStressCapacity(Operation<Float> original) {
        if ((Object) this instanceof HandCrankBlockEntity be) {
            if (be instanceof IMaidHandCrank iCrank && iCrank.muhc$getTick() > 0) {
                float capacity = iCrank.muhc$getStress();
                this.lastCapacityProvided = capacity;
                return capacity;
            }
        }
        return original.call();
    }
}
