package com.sch246.muhc.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.client.AddClothConfigEvent;
import com.sch246.muhc.config.ClothConfigScreen;

public class ClothConfigEvent {
    public static void onEvent(AddClothConfigEvent event) {
        ClothConfigScreen.init(event.getRoot(), event.getEntryBuilder());
    }
}
