package cn.sh1rocu.tlmo_additions.mixin.compat.forgeconfigapiport;

import cn.sh1rocu.tlmo_additions.mixin.compat.forgeconfigapiport.accessor.ModConfigAccessor;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import fuzs.forgeconfigapiport.impl.config.ForgeConfigRegistryImpl;
import fuzs.forgeconfigapiport.impl.core.CommonAbstractions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.nio.file.Path;

@Mixin(ForgeConfigRegistryImpl.class)
public class ForgeConfigRegistryImplMixin {
    @WrapMethod(method = "register(Ljava/lang/String;Lnet/minecraftforge/fml/config/ModConfig$Type;Lnet/minecraftforge/fml/config/IConfigSpec;)Lnet/minecraftforge/fml/config/ModConfig;")
    private ModConfig tlmoa$compatKilt(String modId, ModConfig.Type type, IConfigSpec<?> spec, Operation<ModConfig> original) {
        ModConfig config = original.call(modId, type, spec);
        if (FabricLoader.getInstance().isModLoaded("kilt") && !FabricLoader.getInstance().isModLoaded("kilt_fc_fix")) {
            tlmoa$loadTrackedConfig(config);
        }
        return config;
    }

    @WrapMethod(method = "register(Ljava/lang/String;Lnet/minecraftforge/fml/config/ModConfig$Type;Lnet/minecraftforge/fml/config/IConfigSpec;Ljava/lang/String;)Lnet/minecraftforge/fml/config/ModConfig;")
    private ModConfig tlmoa$compatKilt(String modId, ModConfig.Type type, IConfigSpec<?> spec, String fileName, Operation<ModConfig> original) {
        ModConfig config = original.call(modId, type, spec, fileName);
        if (FabricLoader.getInstance().isModLoaded("kilt") && !FabricLoader.getInstance().isModLoaded("kilt_fc_fix")) {
            tlmoa$loadTrackedConfig(config);
        }
        return config;
    }

    @Unique
    private static void tlmoa$loadTrackedConfig(ModConfig config) {
        if (config.getType() == ModConfig.Type.CLIENT) {
            tlmoa$openConfig(config, CommonAbstractions.INSTANCE.getClientConfigDirectory());
        } else if (config.getType() == ModConfig.Type.COMMON) {
            tlmoa$openConfig(config, CommonAbstractions.INSTANCE.getCommonConfigDirectory());
        }
    }

    @Unique
    private static void tlmoa$openConfig(ModConfig config, Path configBasePath) {
        final CommentedFileConfig configData = config.getHandler().reader(configBasePath).apply(config);
        ((ModConfigAccessor) config).tlmoa$setConfigData(configData);
        // Forge Config API Port: invoke Fabric style callback instead of Forge event
        CommonAbstractions.INSTANCE.fireConfigLoading(config.getModId(), config);
        config.save();
    }
}
