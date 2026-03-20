package studio.fantasyit.maid_useful_task;


import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue SELF_RESCUE = BUILDER
            .define("misc.self_rescue", true);

    private static final ModConfigSpec.BooleanValue ENABLE_LOGGING = BUILDER
            .define("functions.logging", true);
    private static final ModConfigSpec.BooleanValue ENABLE_REVIVE = BUILDER
            .define("functions.revive", true);
    private static final ModConfigSpec.BooleanValue ENABLE_LOCATE = BUILDER
            .define("functions.locate", true);

    private static final ModConfigSpec.BooleanValue ENABLE_REVIVE_AGGRO = BUILDER
            .define("revive.aggro", false);
    private static final ModConfigSpec.BooleanValue ENABLE_REVIVE_TOTEM = BUILDER
            .define("revive.totem", true);

    private static final ModConfigSpec.BooleanValue LOGGING_DISABLE_BLOCKUP = BUILDER
            .define("logging.disable_blockup", false);

    private static final ModConfigSpec.BooleanValue ENABLE_VEHICLE_CONTROL_FULL = BUILDER
            .define("vehicle_control.full", true);
    private static final ModConfigSpec.BooleanValue ENABLE_VEHICLE_CONTROL_ROTATE = BUILDER
            .define("vehicle_control.rotate", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean enableSelfRescue = SELF_RESCUE.get();

    public static boolean enableLoggingTask = ENABLE_LOGGING.get();
    public static boolean enableReviveTask = ENABLE_REVIVE.get();
    public static boolean enableLocateTask = ENABLE_LOCATE.get();

    public static boolean enableReviveAggro = ENABLE_REVIVE_AGGRO.get();
    public static boolean enableReviveTotem = ENABLE_REVIVE_TOTEM.get();

    public static boolean enableVehicleControlFull = ENABLE_VEHICLE_CONTROL_FULL.get();
    public static boolean enableVehicleControlRotate = ENABLE_VEHICLE_CONTROL_ROTATE.get();

    public static boolean disableLoggingBlockUp = LOGGING_DISABLE_BLOCKUP.get();

    static void onLoad(final ModConfig config) {
        enableSelfRescue = SELF_RESCUE.get();
        enableLoggingTask = ENABLE_LOGGING.get();
        enableReviveTask = ENABLE_REVIVE.get();
        enableLocateTask = ENABLE_LOCATE.get();
        enableReviveAggro = ENABLE_REVIVE_AGGRO.get();
        enableReviveTotem = ENABLE_REVIVE_TOTEM.get();
        enableVehicleControlFull = ENABLE_VEHICLE_CONTROL_FULL.get();
        enableVehicleControlRotate = ENABLE_VEHICLE_CONTROL_ROTATE.get();
        disableLoggingBlockUp = LOGGING_DISABLE_BLOCKUP.get();
    }
}