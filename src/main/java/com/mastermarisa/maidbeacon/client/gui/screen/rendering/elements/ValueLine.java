package com.mastermarisa.maidbeacon.client.gui.screen.rendering.elements;

import com.mastermarisa.maidbeacon.client.gui.base.UIBorderBox;
import com.mastermarisa.maidbeacon.client.gui.base.UIElement;
import com.mastermarisa.maidbeacon.client.gui.base.UILabel;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.List;

public class ValueLine extends UIElement {
    private static final int HEIGHT = 20;
    private final UIBorderBox optionBox = new UIBorderBox(new Rectangle(150, HEIGHT), new Color(1, 1, 1, 0.2F));
    private final UIBorderBox valueBox = new UIBorderBox(new Rectangle(30, HEIGHT), new Color(1, 1, 1, 0.2F));
    private final UILabel option;
    private final UILabel value;

    public ValueLine(String option, String value) {
        super(new Rectangle(150, HEIGHT));
        this.option = new UILabel(option, UILabel.TextAlignment.LEFT, Color.WHITE, false);
        this.value = new UILabel(value, UILabel.TextAlignment.LEFT, Color.WHITE, false);
        children = List.of(optionBox, valueBox, this.option, this.value);
    }

    @Override
    protected void render(GuiGraphics graphics, int mouseX, int mouseY) {
        optionBox.setMinX(getMinX());
        optionBox.setMinY(getMinY());
        valueBox.setMinX(getMaxX());
        valueBox.setMinY(getMinY());
        option.setMinX(optionBox.getMinX() + 5);
        option.setCenterY(getCenterY());
        value.setCenter(valueBox.getCenterX(), valueBox.getCenterY());
        super.render(graphics, mouseX, mouseY);
    }
}
