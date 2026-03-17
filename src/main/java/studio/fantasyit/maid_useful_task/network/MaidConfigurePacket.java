package studio.fantasyit.maid_useful_task.network;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import studio.fantasyit.maid_useful_task.MaidUsefulTask;
import studio.fantasyit.maid_useful_task.data.IConfigSetter;
import studio.fantasyit.maid_useful_task.data.MaidConfigKeys;

public class MaidConfigurePacket {
    public static final ResourceLocation ID = new ResourceLocation(MaidUsefulTask.MODID, "configure");

    public static FriendlyByteBuf toBytes(int maidId, ResourceLocation key, String name, String value) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(maidId);
        buffer.writeUtf(key.toString());
        buffer.writeUtf(name);
        buffer.writeUtf(value);
        return buffer;
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int maidId = buf.readInt();
        ResourceLocation key = ResourceLocation.tryParse(buf.readUtf());
        String name = buf.readUtf();
        String value = buf.readUtf();
        server.execute(() -> {
            if (player != null) {
                if (player.level().getEntity(maidId) instanceof EntityMaid entityMaid) {
                    if (MaidConfigKeys.getValue(entityMaid, key) instanceof IConfigSetter ics) {
                        ics.setConfigValue(name, value);
                    }
                }
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void send(EntityMaid maid, ResourceLocation key, String name, String value) {
        ClientPlayNetworking.send(ID, toBytes(maid.getId(), key, name, value));
    }
}
