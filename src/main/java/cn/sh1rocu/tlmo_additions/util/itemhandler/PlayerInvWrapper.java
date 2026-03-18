package cn.sh1rocu.tlmo_additions.util.itemhandler;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.CombinedInvWrapper;
import cn.sh1rocu.touhoulittlemaid.util.itemhandler.PlayerMainInvWrapper;
import net.minecraft.world.entity.player.Inventory;

public class PlayerInvWrapper extends CombinedInvWrapper {
    public PlayerInvWrapper(Inventory inv) {
        super(new PlayerMainInvWrapper(inv), new PlayerArmorInvWrapper(inv), new PlayerOffhandInvWrapper(inv));
    }
}