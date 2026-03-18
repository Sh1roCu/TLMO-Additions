package com.mastermarisa.maidbeacon.item;

import com.mastermarisa.maidbeacon.client.gui.screen.beacon.BeaconScreen;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MobileBeaconItem extends Item {
    public MobileBeaconItem() {
        super((new Properties()).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (level.isClientSide()) BeaconScreen.open(player, usedHand, itemInHand);
        return InteractionResultHolder.success(itemInHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        MobileBeaconData beaconData = MobileBeaconData.getOrDefault(itemStack);
        tooltip.add(Component.literal("信标等级: " + beaconData.getLevel()).withStyle(ChatFormatting.AQUA));
    }
}
