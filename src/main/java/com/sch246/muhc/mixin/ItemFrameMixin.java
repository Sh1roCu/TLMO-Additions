package com.sch246.muhc.mixin;

import net.minecraft.world.entity.decoration.ItemFrame;
import org.spongepowered.asm.mixin.Mixin;

// TODO: wait official Create-Fabric-1.21
@Mixin(ItemFrame.class)
public class ItemFrameMixin {
//    @WrapOperation(method = "survives",
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/world/level/Level;noCollision(Lnet/minecraft/world/entity/Entity;)Z"))
//    private boolean allowCollisionWithCrank(Level level, Entity entity, Operation<Boolean> original) {
//        // 如果与手摇曲柄碰撞，直接返回 true
//        if (level.getBlockEntity(entity.blockPosition()) instanceof HandCrankBlockEntity) {
//            return true;
//        }
//        // 否则使用原版逻辑
//        return original.call(level, entity);
//    }

}
