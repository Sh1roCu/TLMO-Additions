package cn.sh1rocu.tlmo_additions.compat.modmenu;

import com.sch246.muhc.config.ClothConfigIntegration;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ClothConfigIntegration::registerConfigScreen;
    }
}