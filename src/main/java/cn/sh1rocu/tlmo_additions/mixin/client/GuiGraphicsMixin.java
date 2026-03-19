package cn.sh1rocu.tlmo_additions.mixin.client;

import cn.sh1rocu.tlmo_additions.api.extension.gui.GuiGraphicsExtension;
import cn.sh1rocu.tlmo_additions.api.extension.gui.util.GuiHooks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;
import java.util.Optional;

// From PortingLib
@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements GuiGraphicsExtension {

    @Shadow
    public abstract int guiWidth();

    @Shadow
    public abstract int guiHeight();

    @Shadow
    protected abstract void renderTooltipInternal(Font font, List<ClientTooltipComponent> list, int i, int j, ClientTooltipPositioner clientTooltipPositioner);

    @Unique
    private ItemStack tlmoa$tooltipStack = ItemStack.EMPTY;

    @Unique
    @Override
    public void tlmoa$renderComponentTooltip(Font font, List<? extends FormattedText> tooltips, int mouseX, int mouseY, ItemStack stack) {
        this.tlmoa$tooltipStack = stack;
        List<ClientTooltipComponent> components = GuiHooks.gatherTooltipComponents(stack, tooltips, mouseX, guiWidth(), guiHeight(), font);
        this.renderTooltipInternal(font, components, mouseX, mouseY, DefaultTooltipPositioner.INSTANCE);
        this.tlmoa$tooltipStack = ItemStack.EMPTY;
    }

    @ModifyArgs(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V"))
    private void tlmoa$wrapTooltip(Args args, Font font, List<Component> lines, Optional<TooltipComponent> data, int x, int y) {
        if (GuiHooks.MODS_TO_WRAP.contains(BuiltInRegistries.ITEM.getKey(tlmoa$tooltipStack.getItem()).getNamespace())) {
            args.set(1, GuiHooks.gatherTooltipComponents(tlmoa$tooltipStack, lines, data, x, guiWidth(), guiHeight(), font));
        }
    }

    @ModifyArgs(method = "renderComponentTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"))
    private void tlmoa$wrapTooltipComponent(Args args, Font font, List<Component> lines, int x, int y) {
        if (GuiHooks.MODS_TO_WRAP.contains(BuiltInRegistries.ITEM.getKey(tlmoa$tooltipStack.getItem()).getNamespace())) {
            args.set(1, GuiHooks.gatherTooltipComponents(tlmoa$tooltipStack, lines, Optional.empty(), x, guiWidth(), guiHeight(), font));
        }
    }
}
