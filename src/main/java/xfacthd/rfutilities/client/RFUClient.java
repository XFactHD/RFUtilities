package xfacthd.rfutilities.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xfacthd.rfutilities.RFUtilities;
import xfacthd.rfutilities.client.render.EnergyMeterRenderer;
import xfacthd.rfutilities.common.RFUContent;

@Mod.EventBusSubscriber(modid = RFUtilities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RFUClient
{
    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(RFUContent.BLOCK_ENTITY_ENERGY_METER.get(), EnergyMeterRenderer::new);
    }

    public static void onFancyFontConfigChanged(boolean fancyFont)
    {
        EnergyMeterRenderer.onFancyFontConfigChanged(fancyFont);
    }
}
