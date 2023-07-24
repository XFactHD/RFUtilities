package xfacthd.rfutilities;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import xfacthd.rfutilities.client.util.ClientConfig;
import xfacthd.rfutilities.common.RFUContent;
import xfacthd.rfutilities.common.net.Network;
import xfacthd.rfutilities.common.util.ServerConfig;

@Mod(RFUtilities.MOD_ID)
public class RFUtilities
{
    public static final String MOD_ID = "rfutilities";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RFUtilities()
    {
        RFUContent.init();
        Network.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}
