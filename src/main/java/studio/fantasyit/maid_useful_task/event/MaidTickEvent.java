package studio.fantasyit.maid_useful_task.event;

import net.minecraft.server.level.ServerLevel;
import studio.fantasyit.maid_useful_task.task.IMaidVehicleControlTask;
import studio.fantasyit.maid_useful_task.vehicle.MaidVehicleManager;

public class MaidTickEvent {
    public static void onTick(com.github.tartaricacid.touhoulittlemaid.api.event.MaidTickEvent event) {
        if (event.getMaid().level() instanceof ServerLevel sl)
            if (event.getMaid().getTask() instanceof IMaidVehicleControlTask imvc && event.getMaid().getVehicle() != null) {
                imvc.tick(sl, event.getMaid());
                MaidVehicleManager.syncVehicleParameter(event.getMaid());
            } else if (event.getMaid().getVehicle() != null) {
                MaidVehicleManager.stopControlling(event.getMaid());
            }
    }
}