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

package XFactHD.rfutilities.client;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.client.utils.ClientEventHandler;
import XFactHD.rfutilities.client.utils.KeyBindings;
import XFactHD.rfutilities.client.utils.KeyInputHandler;
import XFactHD.rfutilities.common.CommonProxy;
import XFactHD.rfutilities.common.RFUContent;
import XFactHD.rfutilities.common.net.PacketGetThroughput;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        RFUtilities.RFU_NET_WRAPPER.registerMessage(PacketGetThroughput.HandlerPacketGetThroughput.class, PacketGetThroughput.class, 1, Side.CLIENT);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockCapacitor), 0, new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockSwitch), 0, new ModelResourceLocation(Reference.MOD_ID + ":blockSwitch", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockDiode), 0, new ModelResourceLocation(Reference.MOD_ID + ":blockDiode", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockInvisTess), 0, new ModelResourceLocation(Reference.MOD_ID + ":blockInvisibleTesseract", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockResistor), 0, new ModelResourceLocation(Reference.MOD_ID + ":blockResistor", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockTransistor), 0, new ModelResourceLocation(Reference.MOD_ID + ":blockTransistor", "inventory"));
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockRFMeter), 0, new ModelResourceLocation(Reference.MOD_ID + ":blockRFMeter", "inventory"));
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRFMeter.class, new RenderRFMeter());
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}
