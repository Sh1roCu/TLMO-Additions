package studio.fantasyit.maid_useful_task.event;

import cn.sh1rocu.tlmo_additions.api.event.PlayerLoggedOutEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.PlayerLoggedInEvent;
import studio.fantasyit.maid_useful_task.data.MaidReviveGlobalData;

public class PlayerEvents {
    public static void onPlayerEnter(PlayerLoggedInEvent event) {
        MaidReviveGlobalData.clearRescuingMaid(event.getEntity().getUUID());
    }

    public static void onPlayerLeave(PlayerLoggedOutEvent event) {
        MaidReviveGlobalData.clearRescuingMaid(event.getEntity().getUUID());
    }
}
