package com.mastermarisa.maidbeacon;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.entity.data.TaskDataRegister;
import com.github.tartaricacid.touhoulittlemaid.item.bauble.BaubleManager;
import com.mastermarisa.maidbeacon.init.ModItems;
import com.mastermarisa.maidbeacon.init.ModTaskDataKeys;
import com.mastermarisa.maidbeacon.item.bauble.BeaconEffectGeneratorBauble;

@LittleMaidExtension
public class MaidPlugin implements ILittleMaid {
    @Override
    public void registerTaskData(TaskDataRegister register) {
        ModTaskDataKeys.registerAll(register);
    }

    @Override
    public void bindMaidBauble(BaubleManager manager) {
        manager.bind(ModItems.MOBILE_BEACON, new BeaconEffectGeneratorBauble());
    }
}
