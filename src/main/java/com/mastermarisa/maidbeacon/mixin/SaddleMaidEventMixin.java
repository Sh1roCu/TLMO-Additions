package com.mastermarisa.maidbeacon.mixin;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.event.maid.SaddleMaidEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mastermarisa.maidbeacon.entity.ExtraRenderingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SaddleMaidEvent.class)
public class SaddleMaidEventMixin {
    // TODO: 临时修复女仆信标的ExtraRenderingEntity导致鞍无法抱女仆的问题，等原版更新后同步
    @ModifyExpressionValue(method = "onInteract", remap = false, at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 1))
    private static boolean mb$onInteract(boolean original, @Local EntityMaid maid) {
        return original || maid.getPassengers().stream().anyMatch(e -> e instanceof ExtraRenderingEntity);
    }
}
