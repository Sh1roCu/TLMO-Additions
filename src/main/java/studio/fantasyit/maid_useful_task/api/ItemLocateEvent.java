package studio.fantasyit.maid_useful_task.api;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public class ItemLocateEvent extends CancellableEvent {
    public final ItemStack itemStack;
    public final EntityMaid maid;
    public final BlockPos cache;
    public BlockPos target = null;

    public static Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.onItemLocate(event);
        }
    });

    public interface Callback {
        void onItemLocate(ItemLocateEvent event);
    }

    public ItemLocateEvent(ItemStack itemStack, EntityMaid maid, BlockPos cache) {
        this.itemStack = itemStack;
        this.maid = maid;
        this.cache = cache;
    }

    public BlockPos getTarget() {
        return target;
    }

    public void setTarget(BlockPos target) {
        this.target = target;
    }
}