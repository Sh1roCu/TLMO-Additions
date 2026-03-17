package studio.fantasyit.maid_useful_task.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.ItemStack;
import studio.fantasyit.maid_useful_task.MaidUsefulTask;
import studio.fantasyit.maid_useful_task.memory.BlockTargetMemory;
import studio.fantasyit.maid_useful_task.memory.BlockUpContext;
import studio.fantasyit.maid_useful_task.memory.BlockValidationMemory;
import studio.fantasyit.maid_useful_task.memory.CurrentWork;
import studio.fantasyit.maid_useful_task.vehicle.MaidVehicleControlType;

import java.util.Optional;
import java.util.UUID;

public class MemoryModuleRegistry {
    public static final MemoryModuleType<BlockTargetMemory> DESTROY_TARGET
            = register("block_targets", new MemoryModuleType<>(Optional.of(BlockTargetMemory.CODEC)));
    public static final MemoryModuleType<BlockPos> PLACE_TARGET
            = register("place_target", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<BlockUpContext> BLOCK_UP_TARGET
            = register("block_up", new MemoryModuleType<>(Optional.of(BlockUpContext.CODEC)));
    public static final MemoryModuleType<BlockValidationMemory> BLOCK_VALIDATION
            = register("block_validation", new MemoryModuleType<>(Optional.of(BlockValidationMemory.CODEC)));
    public static final MemoryModuleType<BlockPos> COMMON_BLOCK_CACHE
            = register("common_block_cache", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<UUID> REVIVING_PLAYER
            = register("reviving_player", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<ItemStack> LOCATE_ITEM = register("locate_item", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<CurrentWork> CURRENT_WORK = register("current_work", new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<MaidVehicleControlType> IS_ALLOW_HANDLE_VEHICLE = register("is_allow_handle_vehicle", new MemoryModuleType<>(Optional.empty()));

    private static <T> MemoryModuleType<T> register(String name, MemoryModuleType<T> type) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, new ResourceLocation(MaidUsefulTask.MODID, name), type);
    }

    public static void init() {

    }
}
