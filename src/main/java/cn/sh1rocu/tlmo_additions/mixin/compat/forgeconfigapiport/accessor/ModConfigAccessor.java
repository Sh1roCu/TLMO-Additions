package cn.sh1rocu.tlmo_additions.mixin.compat.forgeconfigapiport.accessor;

import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.fml.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModConfig.class)
public interface ModConfigAccessor {
    @Invoker("setConfigData")
    void tlmoa$setConfigData(CommentedConfig configData);
}
