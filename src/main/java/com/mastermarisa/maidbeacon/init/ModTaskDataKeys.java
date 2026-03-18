package com.mastermarisa.maidbeacon.init;

import com.github.tartaricacid.touhoulittlemaid.api.entity.data.TaskDataKey;
import com.github.tartaricacid.touhoulittlemaid.entity.data.TaskDataRegister;
import com.mastermarisa.maidbeacon.data.ExtraRenderSettings;

public class ModTaskDataKeys {
    public static final TaskDataKey<ExtraRenderSettings> EXTRA_RENDER_SETTINGS = new ExtraRenderSettings.DATA_KEY();

    public static void registerAll(TaskDataRegister register) {
        register.register(EXTRA_RENDER_SETTINGS);
    }
}
