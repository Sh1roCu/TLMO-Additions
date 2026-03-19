package cn.sh1rocu.tlmo_additions.mixin.common;

import cn.sh1rocu.tlmo_additions.api.event.EntityLeaveLevelEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Mixin(targets = "net.minecraft.server.level.ServerLevel$EntityCallbacks")
    public abstract static class EntityCallbacksMixin {
        @Shadow
        @Final
        ServerLevel field_26936;

        @Inject(method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
        private void tlmoa$entityLeaveLevelEvent(Entity entity, CallbackInfo ci) {
            EntityLeaveLevelEvent event = new EntityLeaveLevelEvent(entity, this.field_26936);
            EntityLeaveLevelEvent.EVENT.invoker().post(event);
        }
    }
}
