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
import XFactHD.rfutilities.client.render.block.*;
import XFactHD.rfutilities.client.render.item.*;
import XFactHD.rfutilities.client.utils.KeyBindings;
import XFactHD.rfutilities.client.utils.KeyInputHandler;
import XFactHD.rfutilities.common.CommonProxy;
import XFactHD.rfutilities.common.RFUContent;
import XFactHD.rfutilities.common.blocks.tileEntity.*;
import XFactHD.rfutilities.common.net.PacketGetThroughput;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        RFUtilities.RFU_NET_WRAPPER.registerMessage(PacketGetThroughput.HandlerPacketGetThroughput.class, PacketGetThroughput.class, 1, Side.CLIENT);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInvisibleTesseract.class, new RenderInvisTesseract());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCapacitor.class, new RenderCapacitor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySwitch.class, new RenderSwitch());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityResistor.class, new RenderResistor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDiode.class, new RenderDiode());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRFMeter.class, new RenderRFMeter());
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RFUContent.blockCapacitor), new ItemRendererCapacitor());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RFUContent.blockDiode), new ItemRendererDiode());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RFUContent.blockResistor), new ItemRendererResistor());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RFUContent.blockSwitch), new ItemRendererSwitch());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RFUContent.blockInvisTess), new ItemRendererInvisTess());
        //MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RFUContent.blockRFMeter), new ItemRendererRFMeter());
        MinecraftForgeClient.registerItemRenderer(RFUContent.itemMaterialTess, new ItemRendererMaterialTess());
        //MinecraftForgeClient.registerItemRenderer(RFUContent.itemMaterialDisplay, new ItemRendererMaterialDisplay());
        FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        KeyBindings.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}
