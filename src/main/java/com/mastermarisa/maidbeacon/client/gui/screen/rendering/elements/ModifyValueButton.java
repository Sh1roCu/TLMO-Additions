package com.mastermarisa.maidbeacon.client.gui.screen.rendering.elements;

import com.mastermarisa.maidbeacon.client.gui.base.UIButton;
import com.mastermarisa.maidbeacon.client.gui.screen.rendering.RenderSettingsScreen;
import com.mastermarisa.maidbeacon.data.ExtraRenderSettings;
import com.mastermarisa.maidbeacon.network.message.SyncRenderSettingsMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.awt.*;
import java.util.function.Consumer;

public class ModifyValueButton extends UIButton {
    public ModifyValueButton(Rectangle frame, Consumer<ExtraRenderSettings> consumer, RenderSettingsScreen screen) {
        super(frame, (btn) -> ((ModifyValueButton) btn).trigger(consumer, screen), 0);
    }

    public void trigger(Consumer<ExtraRenderSettings> consumer, RenderSettingsScreen screen) {
        consumer.accept(screen.settings);
        screen.refresh();
        SyncRenderSettingsMessage message = new SyncRenderSettingsMessage(screen.maid.getUUID(), screen.settings.serializeNBT());
        ClientPlayNetworking.send(SyncRenderSettingsMessage.ID, SyncRenderSettingsMessage.encode(message));
    }
}
