package com.mastermarisa.maidbeacon.client.gui.screen.beacon.elements;

import com.mastermarisa.maidbeacon.client.gui.base.*;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.data.EffectAura;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.network.OperateEffectPayload;
import com.mastermarisa.maidbeacon.utils.EncodeUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;

import java.awt.*;
import java.util.UUID;

public class UIEffectButton extends UIButton {
    private static final Color COMMON = new Color(1, 1, 1, 0.2F);
    private static final Color HIGHLIGHT = new Color(1, 1, 1, 0.8F);
    private final UIBorderBox borderBox = new UIBorderBox(new Rectangle(21, 21), COMMON);
    private final UIBox bg = new UIBox(new Rectangle(22, 22), COMMON);
    private final UIImage icon;
    private final MobileBeaconData mobileBeaconData;
    private final EffectAura effectAura;
    private final int index;
    private boolean toggled;

    public UIEffectButton(MobileBeaconData mobileBeaconData, EffectAura effectAura, UUID uuid, InteractionHand hand) {
        super(
                new Rectangle(22, 22),
                (btn) -> ((UIEffectButton) btn).trigger(uuid, hand),
                0
        );

        this.mobileBeaconData = mobileBeaconData;
        this.effectAura = effectAura;
        this.index = Config.EFFECT_AURAS().indexOf(effectAura);
        this.toggled = mobileBeaconData.activated.contains(index);

        this.icon = new UIImage(new ImageData(
                effectAura.getIcon(),
                new Rectangle(0, 0, 18, 18),
                18,
                18,
                18,
                18
        ));

        tooltip.add(effectAura.getHolder().value().getDisplayName().copy().append(Component.literal(" " + EncodeUtils.toRoman(effectAura.effectLevel + 1))));
        tooltip.add(Component.literal("Cost: " + effectAura.cost));
        tooltip.add(Component.translatable("gui.maidbeacon.tooltip.range", effectAura.range));
    }

    @Override
    protected void render(GuiGraphics graphics, int mouseX, int mouseY) {
        renderButton(graphics, mouseX, mouseY);
        renderIcon(graphics, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY);
    }

    private void renderButton(GuiGraphics graphics, int mouseX, int mouseY) {
        borderBox.setMinX(getMinX());
        borderBox.setMinY(getMinY());
        if (toggled) {
            borderBox.color = HIGHLIGHT;
            bg.setMinX(getMinX());
            bg.setMinY(getMinY());
            UIElement.render(graphics, bg, mouseX, mouseY);
        } else {
            borderBox.color = COMMON;
        }
        UIElement.render(graphics, borderBox, mouseX, mouseY);
    }

    private void renderIcon(GuiGraphics graphics, int mouseX, int mouseY) {
        icon.setMinX(getMinX() + 2);
        icon.setMinY(getMinY() + 2);
        UIElement.render(graphics, icon, mouseX, mouseY);
    }

    public void trigger(UUID uuid, InteractionHand hand) {
        if (toggled) {
            OperateEffectPayload payload = new OperateEffectPayload(0, index, hand == InteractionHand.MAIN_HAND ? 0 : 1, uuid);
            ClientPlayNetworking.send(payload);
            mobileBeaconData.activated.remove(index);
            toggled = false;
        } else if (mobileBeaconData.getUsedCost() + effectAura.cost <= Config.getCost(mobileBeaconData.getLevel())) {
            OperateEffectPayload payload = new OperateEffectPayload(1, index, hand == InteractionHand.MAIN_HAND ? 0 : 1, uuid);
            ClientPlayNetworking.send(payload);
            mobileBeaconData.activated.add(index);
            toggled = true;
        }
    }
}
