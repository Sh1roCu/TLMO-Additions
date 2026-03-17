package studio.fantasyit.maid_useful_task.compat;

import com.chaosthedude.naturescompass.NaturesCompass;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public class NatureCompass {
    public static BlockPos getCompassTarget(EntityMaid maid, ItemStack itemStack) {
        if (itemStack.is(NaturesCompass.NATURES_COMPASS_ITEM)) {
            return new BlockPos(
                    NaturesCompass.NATURES_COMPASS_ITEM.getFoundBiomeX(itemStack),
                    maid.level().getSeaLevel(),
                    NaturesCompass.NATURES_COMPASS_ITEM.getFoundBiomeZ(itemStack)
            );
        }
        return null;
    }

}
