package cn.sh1rocu.tlmo_additions.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public class PlayerLoggedOutEvent {
    private final Player player;

    public PlayerLoggedOutEvent(Player player) {
        this.player = player;
    }

    public Player getEntity() {
        return player;
    }

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (final Callback callback : callbacks)
            callback.post(event);
    });

    public interface Callback {
        void post(PlayerLoggedOutEvent event);
    }
}
