package studio.fantasyit.maid_useful_task.network;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import studio.fantasyit.maid_useful_task.Config;
import studio.fantasyit.maid_useful_task.MaidUsefulTask;
import studio.fantasyit.maid_useful_task.util.MemoryUtil;
import studio.fantasyit.maid_useful_task.vehicle.MaidVehicleControlType;

public class MaidAllowHandleVehicle {
    public static final ResourceLocation ID = new ResourceLocation(MaidUsefulTask.MODID, "allow_handle_vehicle");

    public static FriendlyByteBuf toBytes(int maidId) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(maidId);
        return buffer;
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            int maidId = buf.readInt();
            Entity entity = player.level().getEntity(maidId);
            if (entity instanceof EntityMaid maid) {
                MaidVehicleControlType[] values = MaidVehicleControlType.values();
                MaidVehicleControlType allowMode = values[(MemoryUtil.getAllowHandleVehicle(maid).ordinal() + 1) % values.length];
                while ((allowMode == MaidVehicleControlType.FULL && !Config.enableVehicleControlFull)
                        || (allowMode == MaidVehicleControlType.ROT_ONLY && !Config.enableVehicleControlRotate)) {
                    allowMode = values[(allowMode.ordinal() + 1) % values.length];
                }
                MemoryUtil.setAllowHandleVehicle(maid, allowMode);
                Component component = switch (allowMode) {
                    case NONE -> Component.translatable("maid_useful_task.allow_handle_vehicle.none");
                    case ROT_ONLY -> Component.translatable("maid_useful_task.allow_handle_vehicle.rot_only");
                    case FULL -> Component.translatable("maid_useful_task.allow_handle_vehicle.full");
                };
                player.sendSystemMessage(component);
            }
        });
    }
}
