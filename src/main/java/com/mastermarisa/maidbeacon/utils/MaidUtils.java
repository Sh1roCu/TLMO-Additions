package com.mastermarisa.maidbeacon.utils;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.handler.BaubleItemHandler;
import com.mastermarisa.maidbeacon.init.ModItems;
import com.mastermarisa.maidbeacon.item.bauble.BeaconEffectGeneratorBauble;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MaidUtils {
    public static ItemStack getEquippedMobileBeacon(EntityMaid maid) {
        BaubleItemHandler handler = maid.getMaidBauble();
        for (int i = 0; i < handler.getSlots(); i++) {
            if (handler.getBaubleInSlot(i) instanceof BeaconEffectGeneratorBauble)
                return handler.getStackInSlot(i);
        }

        return ItemStack.EMPTY;
    }

    public static boolean mobileBeaconEquipped(EntityMaid maid) {
        return maid.getMaidBauble().containsItem(ModItems.MOBILE_BEACON);
    }

    public static List<ItemStack> tryExtract(IItemHandler handler, int count, StackPredicate filter, boolean strict) {
        List<Integer> slots = findStackSlots(handler, filter);
        List<ItemStack> stacks = new ArrayList<>();
        int c = count(handler, filter);
        if (!strict || c >= count) {
            c = 0;
            for (int i : slots) {
                if (c >= count) break;
                ItemStack stack = handler.getStackInSlot(i);
                if (count - c >= stack.getCount()) {
                    c += stack.getCount();
                    stacks.add(handler.extractItem(i, stack.getCount(), false));
                } else {
                    stacks.add(handler.extractItem(i, count - c, false));
                    c = count;
                }
            }
            return stacks;
        }

        return new ArrayList<>();
    }

    public static List<Integer> findStackSlots(IItemHandler handler, StackPredicate filter) {
        IntList slots = new IntArrayList();

        for (int i = 0; i < handler.getSlots(); ++i) {
            ItemStack stack = handler.getStackInSlot(i);
            if (filter.test(stack)) {
                slots.add(i);
            }
        }

        return slots;
    }

    public static int count(IItemHandler handler, StackPredicate predicate) {
        int count = 0;
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (predicate.test(stack)) count += stack.getCount();
        }

        return count;
    }
}
