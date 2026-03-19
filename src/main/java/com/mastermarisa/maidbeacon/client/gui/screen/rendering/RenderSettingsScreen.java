package com.mastermarisa.maidbeacon.client.gui.screen.rendering;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.client.gui.base.UIContainerHorizontal;
import com.mastermarisa.maidbeacon.client.gui.base.UIContainerVertical;
import com.mastermarisa.maidbeacon.client.gui.base.UIElement;
import com.mastermarisa.maidbeacon.client.gui.screen.rendering.elements.ModifyValueButton;
import com.mastermarisa.maidbeacon.client.gui.screen.rendering.elements.ValueLine;
import com.mastermarisa.maidbeacon.data.ExtraRenderSettings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class RenderSettingsScreen extends Screen {
    private static final Minecraft mc;
    private static final Font font;
    private final List<ModifyValueButton> buttons;
    public final ExtraRenderSettings settings;
    public final EntityMaid maid;
    public UIContainerVertical container;

    public RenderSettingsScreen(EntityMaid maid) {
        super(Component.empty());
        this.maid = maid;
        this.settings = maid.getAttachedOrCreate(ExtraRenderSettings.TYPE);
        this.buttons = new ArrayList<>();
        initUI();
    }

    public static void open(EntityMaid maid) {
        mc.setScreen(new RenderSettingsScreen(maid));
    }

    private void initUI() {
        List<UIElement> lines = new ArrayList<>();
        for (var pair : settings.getDisplayPairs()) {
            lines.add(new ValueLine(pair.first(), pair.second()));
        }
        buttons.add(new ModifyValueButton(
                new Rectangle(30, 20),
                (extra) -> {
                    extra.enableRendering = !extra.enableRendering;
                },
                this
        ));
        buttons.add(new ModifyValueButton(
                new Rectangle(30, 20),
                (extra) -> {
                    extra.enableBeaconBeam = !extra.enableBeaconBeam;
                },
                this
        ));
        buttons.add(new ModifyValueButton(
                new Rectangle(30, 20),
                (extra) -> {
                    extra.enableBeaconModel = !extra.enableBeaconModel;
                },
                this
        ));
        buttons.add(new ModifyValueButton(
                new Rectangle(30, 20),
                (extra) -> {
                    extra.enableHeadHalo = !extra.enableHeadHalo;
                },
                this
        ));
        buttons.add(new ModifyValueButton(
                new Rectangle(30, 20),
                (extra) -> {
                    extra.enableSkyHalo = !extra.enableSkyHalo;
                },
                this
        ));
        buttons.add(new ModifyValueButton(
                new Rectangle(30, 20),
                (extra) -> {

                },
                this
        ));
        buttons.add(new ModifyValueButton(
                new Rectangle(30, 20),
                (extra) -> {

                },
                this
        ));

        List<UIElement> parts = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i++) {
            parts.add(UIContainerHorizontal.wrap(List.of(lines.get(i), buttons.get(i)), 0, 0, UIContainerHorizontal.ElementAlignment.LEFT));
        }
        container = UIContainerVertical.wrap(parts, 10, 0, UIContainerVertical.ElementAlignment.UP);
        resize();
    }

    public void refresh() {
        List<UIElement> lines = new ArrayList<>();
        for (var pair : settings.getDisplayPairs()) {
            lines.add(new ValueLine(pair.first(), pair.second()));
        }
        List<UIElement> parts = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i++) {
            parts.add(UIContainerHorizontal.wrap(List.of(lines.get(i), buttons.get(i)), 0, 0, UIContainerHorizontal.ElementAlignment.LEFT));
        }
        container = UIContainerVertical.wrap(parts, 10, 0, UIContainerVertical.ElementAlignment.UP);
        resize();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        UIElement.render(graphics, container, mouseX, mouseY);
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
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void resize() {
        container.setCenterX(getScreenCenterX());
        container.setMinY(20);
    }

    public boolean stillValid() {
        return true;
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
