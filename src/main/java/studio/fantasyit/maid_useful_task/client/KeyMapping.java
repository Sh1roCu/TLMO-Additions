package studio.fantasyit.maid_useful_task.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyMapping {

    public static final net.minecraft.client.KeyMapping KEY_SWITCH_VEHICLE_CONTROL = registerKeyMapping(new net.minecraft.client.KeyMapping(
            "key.maid_useful_tasks.switch_vehicle_control",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.maid_useful_tasks.categories.main"
    ));

    private static net.minecraft.client.KeyMapping registerKeyMapping(net.minecraft.client.KeyMapping key) {
        return KeyBindingHelper.registerKeyBinding(key);
    }

    public static void init() {

    }
}
