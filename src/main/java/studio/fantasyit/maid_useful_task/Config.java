package studio.fantasyit.maid_useful_task;

import com.github.tartaricacid.touhoulittlemaid.entity.task.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;
import java.util.List;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue SELF_RESCUE = BUILDER
            .define("misc.self_rescue", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_LOGGING = BUILDER
            .define("functions.logging", true);
    private static final ForgeConfigSpec.BooleanValue ENABLE_REVIVE = BUILDER
            .define("functions.revive", true);
    private static final ForgeConfigSpec.BooleanValue ENABLE_LOCATE = BUILDER
            .define("functions.locate", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_REVIVE_AGGRO = BUILDER
            .define("revive.aggro", false);
    private static final ForgeConfigSpec.BooleanValue ENABLE_REVIVE_TOTEM = BUILDER
            .define("revive.totem", true);
    private static final ForgeConfigSpec.BooleanValue ENABLE_REVIVE_PASSIVE = BUILDER
            .define("revive.passive", true);
    private static final ForgeConfigSpec.ConfigValue<List<?>> REVIVE_PASSIVE_PRIORITY_1 = BUILDER
            .defineList("revive.passive_priority.1", List.of(
                    TaskIdle.UID.toString()
            ), t -> t instanceof String);
    private static final ForgeConfigSpec.ConfigValue<List<?>> REVIVE_PASSIVE_PRIORITY_2 = BUILDER
            .defineList("revive.passive_priority.2", List.of(), t -> t instanceof String);
    private static final ForgeConfigSpec.ConfigValue<List<?>> REVIVE_PASSIVE_PRIORITY_4 = BUILDER
            .defineList("revive.passive_priority.4", List.of(
                    TaskAttack.UID.toString(),
                    TaskBowAttack.UID.toString(),
                    TaskCrossBowAttack.UID.toString(),
                    TaskDanmakuAttack.UID.toString(),
                    TaskTridentAttack.UID.toString()
            ), t -> t instanceof String);


    private static final ForgeConfigSpec.BooleanValue LOGGING_DISABLE_BLOCKUP = BUILDER
            .define("logging.disable_blockup", false);

    private static final ForgeConfigSpec.BooleanValue ENABLE_VEHICLE_CONTROL_FULL = BUILDER
            .define("vehicle_control.full", true);
    private static final ForgeConfigSpec.BooleanValue ENABLE_VEHICLE_CONTROL_ROTATE = BUILDER
            .define("vehicle_control.rotate", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean enableSelfRescue = SELF_RESCUE.get();

    public static boolean enableLoggingTask = ENABLE_LOGGING.get();
    public static boolean enableReviveTask = ENABLE_REVIVE.get();
    public static boolean enableLocateTask = ENABLE_LOCATE.get();

    public static boolean enableReviveAggro = ENABLE_REVIVE_AGGRO.get();
    public static boolean enableReviveTotem = ENABLE_REVIVE_TOTEM.get();
    public static boolean enableRevivePassive = ENABLE_REVIVE_PASSIVE.get();

    public static boolean enableVehicleControlFull = ENABLE_VEHICLE_CONTROL_FULL.get();
    public static boolean enableVehicleControlRotate = ENABLE_VEHICLE_CONTROL_ROTATE.get();

    public static boolean disableLoggingBlockUp = LOGGING_DISABLE_BLOCKUP.get();

    public static HashMap<ResourceLocation, Integer> passiveReviveJobPriority = new HashMap<>();

    static void onLoad(final ModConfig config) {
        enableSelfRescue = SELF_RESCUE.get();
        enableLoggingTask = ENABLE_LOGGING.get();
        enableReviveTask = ENABLE_REVIVE.get();
        enableLocateTask = ENABLE_LOCATE.get();
        enableReviveAggro = ENABLE_REVIVE_AGGRO.get();
        enableReviveTotem = ENABLE_REVIVE_TOTEM.get();
        enableRevivePassive = ENABLE_REVIVE_PASSIVE.get();
        enableVehicleControlFull = ENABLE_VEHICLE_CONTROL_FULL.get();
        enableVehicleControlRotate = ENABLE_VEHICLE_CONTROL_ROTATE.get();
        disableLoggingBlockUp = LOGGING_DISABLE_BLOCKUP.get();

        passiveReviveJobPriority.clear();
        setPriority(REVIVE_PASSIVE_PRIORITY_1.get(), 1);
        setPriority(REVIVE_PASSIVE_PRIORITY_2.get(), 2);
        setPriority(REVIVE_PASSIVE_PRIORITY_4.get(), 4);
    }

    private static void setPriority(List<?> list, int priority) {
        for (Object op1 : list) {
            if (op1 instanceof String sp1) {
                try {
                    ResourceLocation rl = ResourceLocation.tryParse(sp1);
                    passiveReviveJobPriority.put(rl, priority);
                } catch (Exception e) {
                    MaidUsefulTask.logger.error("When parsing level " + priority + " rl: " + sp1);
                }
            } else {
                MaidUsefulTask.logger.error("not a string: " + op1.toString());
            }
        }
    }
}
