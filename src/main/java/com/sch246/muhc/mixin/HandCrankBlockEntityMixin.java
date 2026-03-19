package com.sch246.muhc.mixin;

import cn.sh1rocu.tlmo_additions.util.Dummy;
import org.spongepowered.asm.mixin.Mixin;

// TODO: wait official Create-Fabric-1.21
// @Mixin(HandCrankBlockEntity.class)
@Mixin(Dummy.class)
public abstract class HandCrankBlockEntityMixin { /*extends GeneratingKineticBlockEntity implements IMaidHandCrank {

    @Unique
    private float muhc$stress = 0;

    @Unique
    private int muhc$tick = 0;

    @Unique
    @Override
    public float muhc$getStress() {
        return muhc$stress;
    }

    @Unique
    @Override
    public int muhc$getTick() {
        return muhc$tick;
    }

    public HandCrankBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Unique
    public void muhc$setStress(float stress, int tick) {
        muhc$stress = stress;
        muhc$tick = tick;
        this.sendData(); // 发送数据包到客户端
    }

    @Inject(method = "write", at = @At("TAIL"))
    private void onWrite(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        // 将自定义数据写入NBT
        compound.putFloat("muhc_stress", muhc$stress);
        compound.putInt("muhc_tick", muhc$tick);
    }

    @Inject(method = "read", at = @At("TAIL"))
    private void onRead(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket, CallbackInfo ci) {
        // 从NBT读取自定义数据
        muhc$stress = compound.getFloat("muhc_stress");
        muhc$tick = compound.getInt("muhc_tick");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (muhc$tick > 0) {
            --muhc$tick;
            if (muhc$tick == 0) {
                muhc$stress = 0;
            }
        }
    }*/
}