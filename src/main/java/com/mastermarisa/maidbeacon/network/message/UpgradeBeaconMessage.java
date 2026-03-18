package com.mastermarisa.maidbeacon.network.message;

import cn.sh1rocu.tlmo_additions.util.itemhandler.PlayerInvWrapper;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.init.ModItems;
import com.mastermarisa.maidbeacon.utils.MaidUtils;
import com.mastermarisa.maidbeacon.utils.StackPredicate;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

public record UpgradeBeaconMessage(int index, int hand, UUID uuid) {
    public static final ResourceLocation ID = MaidBeacon.resourceLocation("upgrade_beacon");

    public static FriendlyByteBuf encode(UpgradeBeaconMessage message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(message.index);
        buf.writeInt(message.hand);
        buf.writeUUID(message.uuid);
        return buf;
    }

    public static UpgradeBeaconMessage decode(FriendlyByteBuf buf) {
        return new UpgradeBeaconMessage(buf.readInt(), buf.readInt(), buf.readUUID());
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var message = decode(buf);
        server.execute(() -> {
            if (player == null) {
                return;
            }
            ServerLevel level = player.serverLevel();
            if (level.getPlayerByUUID(message.uuid) instanceof ServerPlayer serverPlayer) {
                ItemStack itemInHand = serverPlayer.getItemInHand(message.hand == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
                if (itemInHand.is(ModItems.MOBILE_BEACON)) {
                    MobileBeaconData mobileBeaconData = MobileBeaconData.getOrDefault(itemInHand);
                    if (mobileBeaconData.getLevel() == message.index) {
                        ItemStack required = Config.ITEMSTACK_TO_UPGRADE_BEACON_LEVEL().get(message.index);
                        IItemHandler itemHandler = new PlayerInvWrapper(serverPlayer.inventory);
                        WeakReference<IItemHandler> ref = new WeakReference<>(itemHandler);
                        List<ItemStack> itemStackList = MaidUtils.tryExtract(ref.get(), required.getCount(), StackPredicate.of(required.getItem()), true);
                        if (!itemStackList.isEmpty()) {
                            mobileBeaconData.setLevel(mobileBeaconData.getLevel() + 1);
                        }
                        itemHandler = null;
                    }
                    MobileBeaconData.setData(itemInHand, mobileBeaconData);
                }
            }
        });
    }
}
