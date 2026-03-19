package com.mastermarisa.maidbeacon.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.client.gui.screen.rendering.RenderSettingsScreen;
import com.mastermarisa.maidbeacon.init.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class OnInteractMaid {
    public static InteractionResult onPlayerInteractMaidEvent(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (entity instanceof EntityMaid maid) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (itemStack.is(ModItems.MOBILE_BEACON)) {
                if (world.isClientSide())
                    RenderSettingsScreen.open(maid);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
