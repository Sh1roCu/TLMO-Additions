package com.sch246.muhc.mixin;

import cn.sh1rocu.tlmo_additions.util.Dummy;
import org.spongepowered.asm.mixin.Mixin;

// TODO: wait official Create-Fabric-1.21
// @Mixin(KineticBlockEntity.class)
@Mixin(Dummy.class)
public abstract class KineticBlockEntityMixin {
//    @Shadow(remap = false)
//    protected float lastCapacityProvided;
//
//    @WrapMethod(remap = false, method = "calculateAddedStressCapacity")
//    private float override$calculateAddedStressCapacity(Operation<Float> original) {
//        if ((Object) this instanceof HandCrankBlockEntity be) {
//            if (be instanceof IMaidHandCrank iCrank && iCrank.muhc$getTick() > 0) {
//                float capacity = iCrank.muhc$getStress();
//                this.lastCapacityProvided = capacity;
//                return capacity;
//            }
//        }
//        return original.call();
//    }
}
