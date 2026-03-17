package studio.fantasyit.maid_useful_task.client;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import studio.fantasyit.maid_useful_task.network.MaidAllowHandleVehicle;

import static studio.fantasyit.maid_useful_task.client.KeyMapping.KEY_SWITCH_VEHICLE_CONTROL;

@Environment(EnvType.CLIENT)
public class KeyEvents {
    public static void keyInput(int key, int scanCode, int action, int mods) {
        while (KEY_SWITCH_VEHICLE_CONTROL.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            if (player == null || !player.isPassenger()) return;
            EntityMaid maid = player
                    .getVehicle()
                    .getPassengers()
                    .stream()
                    .filter(entity -> entity instanceof EntityMaid)
                    .map(entity -> (EntityMaid) entity)
                    .findAny()
                    .orElse(null);
            if (maid == null) return;
            ClientPlayNetworking.send(MaidAllowHandleVehicle.ID, MaidAllowHandleVehicle.toBytes(maid.getId()));
        }
    }
}
