package studio.fantasyit.maid_useful_task.compat;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public class CompatEntry {
    public static BlockPos getLocateTarget(EntityMaid maid, ItemStack itemStack) {
        BlockPos tmp = null;
        if (tmp == null && FabricLoader.getInstance().isModLoaded("naturescompass")) {
            tmp = NatureCompass.getCompassTarget(maid, itemStack);
        }
        if (tmp == null && FabricLoader.getInstance().isModLoaded("explorerscompass")) {
            tmp = ExplorerCompass.getCompassTarget(maid, itemStack);
        }

        return tmp;
    }
}
