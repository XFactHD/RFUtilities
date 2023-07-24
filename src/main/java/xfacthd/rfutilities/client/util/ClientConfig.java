package xfacthd.rfutilities.client.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.commons.lang3.tuple.Pair;
import xfacthd.rfutilities.client.RFUClient;

public class ClientConfig
{
    public static final ForgeConfigSpec SPEC;
    public static final ClientConfig INSTANCE;

    private final ForgeConfigSpec.BooleanValue useFancyFont;

    static
    {
        final Pair<ClientConfig, ForgeConfigSpec> configSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = configSpecPair.getRight();
        INSTANCE = configSpecPair.getLeft();
    }

    private ClientConfig(ForgeConfigSpec.Builder builder)
    {
        builder.push("font");

        useFancyFont = builder.comment("Whether the Energy Meter should use a fancy seven-segment style font")
                .translation("config.energy_meter.render.fancy_font")
                .define("energyMeter_useFancyFont", false);

        builder.pop();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigReload);
    }

    private void onConfigReload(final ModConfigEvent event)
    {
        ModConfig config = event.getConfig();
        if (FMLEnvironment.dist.isClient() && config.getType() == ModConfig.Type.CLIENT && config.getSpec() == SPEC)
        {
            RFUClient.onFancyFontConfigChanged(useFancyFont.get()); //TODO: check if the unboxing causes unwanted classloading
        }
    }
}
