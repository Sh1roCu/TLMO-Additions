package cn.sh1rocu.tlmo_additions.api.extension.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;

import java.util.List;

// From PortingLib
public interface GuiGraphicsExtension {
    default void tlmoa$renderComponentTooltip(Font font, List<? extends FormattedText> tooltips, int mouseX, int mouseY, ItemStack stack) {
        throw new RuntimeException("Mixin failed!");
    }
}