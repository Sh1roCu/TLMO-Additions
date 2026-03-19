package com.mastermarisa.maidbeacon.client.gui.screen.beacon;

import com.mastermarisa.maidbeacon.client.gui.base.UIContainerHorizontal;
import com.mastermarisa.maidbeacon.client.gui.base.UIContainerVertical;
import com.mastermarisa.maidbeacon.client.gui.base.UIElement;
import com.mastermarisa.maidbeacon.client.gui.screen.beacon.elements.UIEffectButton;
import com.mastermarisa.maidbeacon.client.gui.screen.beacon.elements.UIUpgradeButton;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.init.ModDataComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;


@ParametersAreNonnullByDefault
@Environment(EnvType.CLIENT)
public class BeaconScreen extends Screen {
    private static final Minecraft mc;
    private static final Font font;

    private final Player player;
    private final InteractionHand hand;
    private final ItemStack itemStack;
    private double YOffset;

    private List<List<UIElement>> lines;
    private UIContainerVertical container;

    private BeaconScreen(Player player, InteractionHand hand, ItemStack itemStack) {
        super(Component.empty());
        this.player = player;
        this.hand = hand;
        this.itemStack = itemStack;
        initUI();
    }

    public static void open(Player player, InteractionHand hand, ItemStack itemStack) {
        Minecraft.getInstance().setScreen(new BeaconScreen(player, hand, itemStack));
    }

    public void initUI() {
        MobileBeaconData mobileBeaconData = player.getItemInHand(hand).getOrDefault(ModDataComponents.MOBILE_BEACON_DATA, new MobileBeaconData());
        lines = new ArrayList<>();
        List<UIElement> parts = new ArrayList<>();
        lines.add(List.of(new UIUpgradeButton(-1, player, hand, this)));
        parts.add(UIContainerHorizontal.wrap(lines.getLast(), 16, 0, UIContainerHorizontal.ElementAlignment.CENTER));
        for (int i = 0; i < Math.min(mobileBeaconData.getLevel(), Config.MAX_LEVEL()) + 1; i++) {
            List<UIElement> elements = new ArrayList<>();
            for (var aura : Config.BY_LEVEL(i)) {
                elements.add(new UIEffectButton(
                        mobileBeaconData,
                        aura,
                        player.getUUID(),
                        hand
                ));
            }
            lines.add(elements);
            parts.add(UIContainerHorizontal.wrap(elements, 16, 0, UIContainerHorizontal.ElementAlignment.CENTER));
            if (i < Config.ITEMSTACK_TO_UPGRADE_BEACON_LEVEL().size()) {
                lines.add(List.of(new UIUpgradeButton(i, player, hand, this)));
                parts.add(UIContainerHorizontal.wrap(lines.getLast(), 16, 0, UIContainerHorizontal.ElementAlignment.CENTER));
            }
        }
        container = UIContainerVertical.wrap(parts, 16, 0, UIContainerVertical.ElementAlignment.UP);
        resize();
        container.order();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        UIElement.render(graphics, container, mouseX, mouseY);
        UIElement.renderToolTip(graphics, container, mouseX, mouseY);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        resize();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        UIElement.onMouseClicked(container, mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY != 0) {
            YOffset = Math.clamp(YOffset + scrollY * 10, -Double.MAX_VALUE, 0);
            resize();
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void resize() {
        container.setCenterX(getScreenCenterX());
        container.setMinY((int) (50 + YOffset));
    }

    public boolean stillValid() {
        return player.getItemInHand(hand).equals(itemStack);
    }

    public static int getScreenCenterX() {
        return mc.getWindow().getGuiScaledWidth() / 2;
    }

    public static int getScreenCenterY() {
        return mc.getWindow().getGuiScaledHeight() / 2;
    }

    public static void close() {
        mc.setScreen(null);
    }

    static {
        mc = Minecraft.getInstance();
        font = mc.font;
    }
}
