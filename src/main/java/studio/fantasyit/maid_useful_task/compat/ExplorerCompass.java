package studio.fantasyit.maid_useful_task.compat;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public class ExplorerCompass {
    public static BlockPos getCompassTarget(EntityMaid maid, ItemStack itemStack) {
        if (itemStack.is(ExplorersCompass.EXPLORERS_COMPASS_ITEM)) {
            return new BlockPos(
                    ExplorersCompass.EXPLORERS_COMPASS_ITEM.getFoundStructureX(itemStack),
                    maid.level().getSeaLevel(),
                    ExplorersCompass.EXPLORERS_COMPASS_ITEM.getFoundStructureZ(itemStack)
            );
        }
        return null;
    }

}
