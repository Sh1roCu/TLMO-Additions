package com.sch246.muhc.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFrame.class)
public class ItemFrameMixin {
    @WrapOperation(method = "survives",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;noCollision(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean allowCollisionWithCrank(Level level, Entity entity, Operation<Boolean> original) {
        // 如果与手摇曲柄碰撞，直接返回 true
        if (level.getBlockEntity(entity.blockPosition()) instanceof HandCrankBlockEntity) {
            return true;
        }
        // 否则使用原版逻辑
        return original.call(level, entity);
    }

}
