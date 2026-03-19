package com.mastermarisa.maidbeacon.client.gui.screen.beacon.elements;

import cn.sh1rocu.tlmo_additions.util.itemhandler.PlayerInvWrapper;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import com.mastermarisa.maidbeacon.client.gui.base.*;
import com.mastermarisa.maidbeacon.client.gui.screen.beacon.BeaconScreen;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.init.ModDataComponents;
import com.mastermarisa.maidbeacon.init.ModItems;
import com.mastermarisa.maidbeacon.network.UpgradeBeaconPayload;
import com.mastermarisa.maidbeacon.utils.MaidUtils;
import com.mastermarisa.maidbeacon.utils.StackPredicate;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class UIUpgradeButton extends UIButton {
    private static final Color COMMON = new Color(1, 1, 1, 0.2F);
    private static final Color HIGHLIGHT = new Color(1, 1, 1, 0.8F);
    private final UIBorderBox borderBox = new UIBorderBox(new Rectangle(21, 21), COMMON);
    private final UIBox bg = new UIBox(new Rectangle(22, 22), COMMON);
    private final UIItemStack icon;
    private final Player player;
    private final IItemHandler playerItemHandler;
    private final InteractionHand hand;
    private final int index;

    public UIUpgradeButton(int index, Player player, InteractionHand hand, BeaconScreen screen) {
        super(
                new Rectangle(22, 22),
                (btn) -> ((UIUpgradeButton) btn).trigger(screen),
                0
        );

        this.index = index;
        this.player = player;
        this.playerItemHandler = new PlayerInvWrapper(player.inventory);
        this.hand = hand;
        ItemStack itemStack = index == -1 ? new ItemStack(ModItems.MOBILE_BEACON) : Config.ITEMSTACK_TO_UPGRADE_BEACON_LEVEL().get(index).copy();
        icon = new UIItemStack(itemStack);
        icon.renderTooltip = false;
    }

    @Override
    protected void render(GuiGraphics graphics, int mouseX, int mouseY) {
        icon.setMinX(getMinX() + 3);
        icon.setMinY(getMinY() + 3);
        borderBox.setMinX(getMinX());
        borderBox.setMinY(getMinY());
        if (unlocked()) {
            borderBox.color = HIGHLIGHT;
            bg.setMinX(getMinX());
            bg.setMinY(getMinY());
            UIElement.render(graphics, bg, mouseX, mouseY);
        } else {
            borderBox.color = COMMON;
        }
        UIElement.render(graphics, borderBox, mouseX, mouseY);
        UIElement.render(graphics, icon, mouseX, mouseY);

        super.render(graphics, mouseX, mouseY);
    }

    private boolean unlocked() {
        ItemStack itemInHand = player.getItemInHand(hand);
        boolean result = index == -1 || (itemInHand.is(ModItems.MOBILE_BEACON)
                && itemInHand.getOrDefault(ModDataComponents.MOBILE_BEACON_DATA, new MobileBeaconData()).getLevel() > index);

        tooltip.clear();
        if (result) {
            tooltip.add(Component.translatable("jade.maidbeacon.tooltip.beacon_level", index + 1).withStyle(ChatFormatting.AQUA));
        } else {
            tooltip.add(Component.translatable("jade.maidbeacon.tooltip.beacon_level", index + 1).withStyle(ChatFormatting.GRAY));
            ItemStack required = Config.ITEMSTACK_TO_UPGRADE_BEACON_LEVEL().get(index);
            tooltip.add(Component.translatable("gui.maidbeacon.tooltip.locked", required.getHoverName().getString() + " x" + required.getCount()).withStyle(ChatFormatting.GRAY));
        }

        return result;
    }

    public void trigger(BeaconScreen screen) {
        if (unlocked()) return;
        ItemStack itemStack = Config.ITEMSTACK_TO_UPGRADE_BEACON_LEVEL().get(index).copy();
        if (MaidUtils.count(playerItemHandler, StackPredicate.of(itemStack.getItem())) >= itemStack.getCount()) {
            UpgradeBeaconPayload payload = new UpgradeBeaconPayload(index, player.getUUID(), hand == InteractionHand.MAIN_HAND ? 0 : 1);
            ClientPlayNetworking.send(payload);
            ItemStack itemInHand = player.getItemInHand(hand);
            MobileBeaconData mobileBeaconData = itemInHand.getOrDefault(ModDataComponents.MOBILE_BEACON_DATA, new MobileBeaconData());
            mobileBeaconData.setLevel(mobileBeaconData.getLevel() + 1);
            itemInHand.set(ModDataComponents.MOBILE_BEACON_DATA, mobileBeaconData);
            screen.initUI();
        }
    }
}
