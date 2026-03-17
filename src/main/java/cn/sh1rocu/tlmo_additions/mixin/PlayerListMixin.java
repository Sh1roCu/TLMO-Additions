package cn.sh1rocu.tlmo_additions.mixin;

import cn.sh1rocu.tlmo_additions.api.event.PlayerLoggedOutEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Inject(method = "remove", at = @At("HEAD"))
    private void tlmoa$playerLoggedOut(ServerPlayer player, CallbackInfo ci) {
        PlayerLoggedOutEvent event = new PlayerLoggedOutEvent(player);
        PlayerLoggedOutEvent.EVENT.invoker().post(event);
    }
}