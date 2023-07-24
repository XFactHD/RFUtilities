package xfacthd.rfutilities.common.util;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import xfacthd.rfutilities.common.data.CapacitorType;

public class ServerConfig
{
    public static final ForgeConfigSpec SPEC;
    public static final ServerConfig INSTANCE;

    public final ImmutableMap<CapacitorType, ForgeConfigSpec.IntValue> capacitorCapacity;
    public final ImmutableMap<CapacitorType, ForgeConfigSpec.IntValue> capacitorInput;
    public final ImmutableMap<CapacitorType, ForgeConfigSpec.IntValue> capacitorOutput;

    static
    {
        final Pair<ServerConfig, ForgeConfigSpec> configSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SPEC = configSpecPair.getRight();
        INSTANCE = configSpecPair.getLeft();
    }

    private ServerConfig(ForgeConfigSpec.Builder builder)
    {
        builder.push("capacitor");

        ImmutableMap.Builder<CapacitorType, ForgeConfigSpec.IntValue> capacityBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<CapacitorType, ForgeConfigSpec.IntValue> inputBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<CapacitorType, ForgeConfigSpec.IntValue> outputBuilder = ImmutableMap.builder();

        for (CapacitorType type : CapacitorType.values())
        {
            if (!type.isCreative())
            {
                capacityBuilder.put(
                        type,
                        builder.comment("The capacity of the " + type.getFriendlyName())
                                .translation("config.capacitor.capacity." + type.getName())
                                .defineInRange("capacity_" + type.getName(), type.getDefaultCapacity(), 1000, Integer.MAX_VALUE)
                );

                inputBuilder.put(
                        type,
                        builder.comment("The maximum input of the " + type.getFriendlyName())
                                .translation("config.capacitor.input." + type.getName())
                                .defineInRange("input_" + type.getName(), type.getDefaultInput(), 100, Integer.MAX_VALUE)
                );
            }

            outputBuilder.put(
                    type,
                    builder.comment("The maximum output of the " + type.getFriendlyName())
                            .translation("config.capacitor.output." + type.getName())
                            .defineInRange("output_" + type.getName(), type.getDefaultOutput(), 100, Integer.MAX_VALUE)
            );
        }

        capacitorCapacity = capacityBuilder.build();
        capacitorInput = inputBuilder.build();
        capacitorOutput = outputBuilder.build();

        builder.pop();
    }



    public static int getCapacitorCapacity(CapacitorType type)
    {
        ForgeConfigSpec.IntValue value = INSTANCE.capacitorCapacity.get(type);
        return value != null ? value.get() : type.getDefaultCapacity();
    }

    public static int getCapacitorInput(CapacitorType type)
    {
        ForgeConfigSpec.IntValue value = INSTANCE.capacitorInput.get(type);
        return value != null ? value.get() : type.getDefaultInput();
    }

    public static int getCapacitorOutput(CapacitorType type)
    {
        ForgeConfigSpec.IntValue value = INSTANCE.capacitorOutput.get(type);
        return value != null ? value.get() : type.getDefaultOutput();
    }
}
