package studio.fantasyit.maid_useful_task.network;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import studio.fantasyit.maid_useful_task.MaidUsefulTask;
import studio.fantasyit.maid_useful_task.vehicle.MaidVehicleManager;

public class MaidSyncVehiclePacket {
    public static final ResourceLocation ID = new ResourceLocation(MaidUsefulTask.MODID, "sync_vehicle");

    public static FriendlyByteBuf toBytes(int maidId, CompoundTag tag) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(maidId);
        buffer.writeNbt(tag);
        return buffer;
    }

    @Environment(EnvType.CLIENT)
    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int maidId = buf.readInt();
        CompoundTag tag = buf.readNbt();
        client.execute(() -> {
            Entity entity = Network.getLocalPlayer().level().getEntity(maidId);
            if (entity instanceof EntityMaid maid) {
                MaidVehicleManager.getControllableVehicle(maid).ifPresent(vehicle -> {
                    vehicle.syncVehicleParameter(maid, tag);
                });
            }
        });
    }
}
