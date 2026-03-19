package com.mastermarisa.maidbeacon.event;

import cn.sh1rocu.tlmo_additions.api.event.EntityLeaveLevelEvent;
import cn.sh1rocu.touhoulittlemaid.api.event.EntityJoinLevelEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.entity.ExtraRenderingEntity;
import net.minecraft.server.level.ServerLevel;

public class MaidTracker {
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity().getType() == EntityMaid.TYPE) {
            EntityMaid maid = (EntityMaid) event.getEntity();
            ExtraRenderingEntity extraRendering = new ExtraRenderingEntity(ExtraRenderingEntity.TYPE, event.getLevel());
            extraRendering.setOwnerID(maid.getId());
            extraRendering.setPos(maid.position());
            event.getLevel().addFreshEntity(extraRendering);
        }
    }

    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity().getType() == EntityMaid.TYPE) {
            EntityMaid maid = (EntityMaid) event.getEntity();
            ServerLevel level = (ServerLevel) event.getLevel();
            level.getEntities().getAll().forEach(e -> {
                if (e instanceof ExtraRenderingEntity extraRendering && extraRendering.getOwnerID() == maid.getId()) {
                    extraRendering.discard();
                }
            });
        }
    }
}
