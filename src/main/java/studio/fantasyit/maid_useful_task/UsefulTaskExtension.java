package studio.fantasyit.maid_useful_task;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.api.entity.ai.IExtraMaidBrain;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.ExtraMaidBrainManager;
import com.github.tartaricacid.touhoulittlemaid.entity.data.TaskDataRegister;
import com.github.tartaricacid.touhoulittlemaid.entity.item.control.BroomControlManager;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import studio.fantasyit.maid_useful_task.compat.PlayerRevive;
import studio.fantasyit.maid_useful_task.data.MaidConfigKeys;
import studio.fantasyit.maid_useful_task.data.MaidLoggingConfig;
import studio.fantasyit.maid_useful_task.data.MaidReviveConfig;
import studio.fantasyit.maid_useful_task.registry.MemoryModuleRegistry;
import studio.fantasyit.maid_useful_task.task.MaidLocateTask;
import studio.fantasyit.maid_useful_task.task.MaidRevivePlayerTask;
import studio.fantasyit.maid_useful_task.task.MaidTreeTask;
import studio.fantasyit.maid_useful_task.vehicle.broom.BroomController;

import java.util.List;

@LittleMaidExtension
public class UsefulTaskExtension implements ILittleMaid {
    @Override
    public void addMaidTask(TaskManager manager) {
        ILittleMaid.super.addMaidTask(manager);
        if (Config.enableLoggingTask)
            manager.add(new MaidTreeTask());
        if (Config.enableLocateTask)
            manager.add(new MaidLocateTask());
        if (Config.enableReviveTask)
            if (PlayerRevive.isEnable())
                manager.add(new MaidRevivePlayerTask());
    }

    @Override
    public void addExtraMaidBrain(ExtraMaidBrainManager manager) {
        manager.addExtraMaidBrain(new IExtraMaidBrain() {
            @Override
            public List<MemoryModuleType<?>> getExtraMemoryTypes() {
                return List.of(
                        MemoryModuleRegistry.DESTROY_TARGET,
                        MemoryModuleRegistry.PLACE_TARGET,
                        MemoryModuleRegistry.BLOCK_UP_TARGET,
                        MemoryModuleRegistry.BLOCK_VALIDATION,
                        MemoryModuleRegistry.CURRENT_WORK,
                        MemoryModuleRegistry.COMMON_BLOCK_CACHE,
                        MemoryModuleRegistry.IS_ALLOW_HANDLE_VEHICLE,
                        MemoryModuleRegistry.LOCATE_ITEM
                );
            }
        });
    }

    @Override
    public void registerTaskData(TaskDataRegister register) {
        MaidConfigKeys.addKey(MaidLoggingConfig.LOCATION,
                MaidLoggingConfig.KEY = register.register(new MaidLoggingConfig()),
                MaidLoggingConfig.Data::getDefault);
        MaidConfigKeys.addKey(MaidReviveConfig.LOCATION,
                MaidReviveConfig.KEY = register.register(new MaidReviveConfig()),
                MaidReviveConfig.Data::getDefault);
    }

    @Override
    public void registerBroomControl(BroomControlManager register) {
        register.register(BroomController::new);
    }
}