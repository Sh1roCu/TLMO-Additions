package com.mastermarisa.maidbeacon.client.gui;

import com.mastermarisa.maidbeacon.client.gui.base.ImageData;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public interface UIConst {
    ImageData buttonImg = new ImageData(
            ResourceLocation.parse("minecraft:textures/gui/sprites/container/beacon/button.png"),
            new Rectangle(0, 0, 22, 22),
            22, 22,
            22, 22
    );

    ImageData disabledImg = new ImageData(
            ResourceLocation.parse("minecraft:textures/gui/sprites/container/beacon/button_disabled.png"),
            new Rectangle(0, 0, 22, 22),
            22, 22,
            22, 22
    );
}
