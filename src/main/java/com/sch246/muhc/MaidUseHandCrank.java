package com.sch246.muhc;

import cn.sh1rocu.tlmo_additions.TLMOAdditions;
import com.mojang.logging.LogUtils;
import com.sch246.muhc.create.InitPoi;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

public class MaidUseHandCrank {
    public static final String MODID = "muhc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ForgeConfigRegistry.INSTANCE.register(TLMOAdditions.MODID, ModConfig.Type.COMMON, Config.SPEC, MODID + "-common.toml");
        InitPoi.init();
        // 注册 Mods 界面的“设置”按钮与界面
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            LOGGER.info("[{}] Registering config screen...", MODID);
        }
    }
}
