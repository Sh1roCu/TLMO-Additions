package com.sch246.muhc.maid;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.api.event.client.AddClothConfigEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
import com.sch246.muhc.event.ClothConfigEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

@LittleMaidExtension
public class LittleMaidCompat implements ILittleMaid {
    // 默认构造函数，女仆模组会在合适的时间调用这个构造函数。可以在这里注册女仆专属的事件
    public LittleMaidCompat() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT &&
                FabricLoader.getInstance().isModLoaded("cloth-config")) {
            AddClothConfigEvent.CALLBACK.register(ClothConfigEvent::onEvent);
        }
    }

    /**
     * 注册女仆工作任务的方法
     * <p>
     * Method to add maid work tasks
     */
    @Override
    public void addMaidTask(TaskManager manager) {
        if (!FabricLoader.getInstance().isModLoaded("create"))
            return;
        // 添加自定义任务
        // TODO: wait official Create-Fabric-1.21
        // manager.add(new HandCrankTask());
    }
}
