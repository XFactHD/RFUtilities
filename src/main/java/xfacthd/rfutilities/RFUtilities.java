package xfacthd.rfutilities;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import xfacthd.rfutilities.common.RFUContent;

@Mod(RFUtilities.MOD_ID)
public class RFUtilities
{
    public static final String MOD_ID = "rfutilities";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RFUtilities()
    {
        RFUContent.init();
    }
}
