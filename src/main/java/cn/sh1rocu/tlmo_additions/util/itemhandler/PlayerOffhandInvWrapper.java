package cn.sh1rocu.tlmo_additions.util.itemhandler;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.InvWrapper;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.RangedWrapper;
import net.minecraft.world.entity.player.Inventory;

public class PlayerOffhandInvWrapper extends RangedWrapper {
    public PlayerOffhandInvWrapper(Inventory inv) {
        super(new InvWrapper(inv), inv.items.size() + inv.armor.size(), inv.items.size() + inv.armor.size() + inv.offhand.size());
    }
}