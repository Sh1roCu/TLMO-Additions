package cn.sh1rocu.tlmo_additions.mixin.client;

import cn.sh1rocu.tlmo_additions.api.event.EntityLeaveLevelEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Mixin(targets = "net.minecraft.client.multiplayer.ClientLevel$EntityCallbacks")
    public abstract static class EntityCallbacksMixin {
        @Shadow
        @Final
        ClientLevel field_27735;

        @Inject(method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
        private void tlmoa$entityLeaveLevelEvent(Entity entity, CallbackInfo ci) {
            EntityLeaveLevelEvent event = new EntityLeaveLevelEvent(entity, this.field_27735);
            EntityLeaveLevelEvent.EVENT.invoker().post(event);
        }
    }
}
