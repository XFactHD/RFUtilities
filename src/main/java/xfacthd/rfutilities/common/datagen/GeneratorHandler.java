package xfacthd.rfutilities.common.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xfacthd.rfutilities.RFUtilities;
import xfacthd.rfutilities.common.datagen.providers.*;

@Mod.EventBusSubscriber(modid = RFUtilities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeneratorHandler
{
    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        boolean client = event.includeClient();
        boolean server = event.includeServer();

        gen.addProvider(client, new RFUBlockStateProvider(gen, fileHelper));
    }
}
