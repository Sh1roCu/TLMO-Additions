package com.mastermarisa.maidbeacon.client.gui.base;

import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class UIBorderBox extends UIElement {
    public Color color;

    public UIBorderBox(Rectangle frame, Color color) {
        super(frame);
        this.color = color;
    }

    @Override
    protected void render(GuiGraphics graphics, int mouseX, int mouseY) {
        super.render(graphics,mouseX,mouseY);
        graphics.fill(this.frame.x, this.frame.y, this.frame.x + this.frame.width, this.frame.y + 1, this.color.getRGB());
        graphics.fill(this.frame.x + 1, this.frame.y + this.frame.height, this.frame.x + this.frame.width + 1, this.frame.y + this.frame.height + 1, this.color.getRGB());
        graphics.fill(this.frame.x, this.frame.y + 1, this.frame.x + 1, this.frame.y + this.frame.height + 1, this.color.getRGB());
        graphics.fill(this.frame.x + this.frame.width, this.frame.y, this.frame.x + this.frame.width + 1, this.frame.y + this.frame.height, this.color.getRGB());
    }
}
