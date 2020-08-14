/*  Copyright (C) <2015>  <XFactHD>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses. */

package XFactHD.rfutilities.common;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.net.PacketDialerChangeMode;
import XFactHD.rfutilities.common.net.PacketSetThroughput;
import XFactHD.rfutilities.common.net.PacketSetTransferMode;
import XFactHD.rfutilities.common.net.PacketWantThroughput;
import XFactHD.rfutilities.common.utils.ConfigHandler;
import XFactHD.rfutilities.common.utils.EventHandler;
import XFactHD.rfutilities.common.utils.GuiFactory;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
        RFUtilities.RFU_NET_WRAPPER.registerMessage(PacketWantThroughput.HandlerPacketWantThroughput.class, PacketWantThroughput.class, 0, Side.SERVER);
        RFUtilities.RFU_NET_WRAPPER.registerMessage(PacketSetThroughput.HandlerPacketSetThroughput.class, PacketSetThroughput.class, 2, Side.SERVER);
        RFUtilities.RFU_NET_WRAPPER.registerMessage(PacketDialerChangeMode.HandlerPacketDialerChangeMode.class, PacketDialerChangeMode.class, 3, Side.SERVER);
        RFUtilities.RFU_NET_WRAPPER.registerMessage(PacketSetTransferMode.HandlerPacketSetTransferMode.class, PacketSetTransferMode.class, 4, Side.SERVER);
        RFUContent.preInit();
    }

    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(RFUtilities.instance, new GuiFactory());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        RFUContent.init();
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        RFUContent.postInit();
    }
}
