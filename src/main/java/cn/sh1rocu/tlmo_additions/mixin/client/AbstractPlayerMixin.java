package cn.sh1rocu.tlmo_additions.mixin.client;

import cn.sh1rocu.tlmo_additions.api.event.ComputeFovModifierEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractPlayerMixin extends Player {
    public AbstractPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @WrapOperation(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F"))
    private float tlmoa$getForgeFovModifier(float delta, float start, float end, Operation<Float> original) {
        ComputeFovModifierEvent fovModifierEvent = new ComputeFovModifierEvent(this, end);
        ComputeFovModifierEvent.EVENT.invoker().post(fovModifierEvent);
        return fovModifierEvent.getNewFovModifier();
    }
}
