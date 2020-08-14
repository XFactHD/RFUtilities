/*  Copyright (C) <2016>  <XFactHD>

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

import XFactHD.rfutilities.client.utils.KeyBindings;
import XFactHD.rfutilities.client.utils.KeyInputHandler;
import XFactHD.rfutilities.common.CommonProxy;
import XFactHD.rfutilities.common.RFUContent;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    @SuppressWarnings("ConstantConditions")
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockCapacitor),  0, new ModelResourceLocation(RFUContent.blockCapacitor.getRegistryName(), "inv1"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockCapacitor),  1, new ModelResourceLocation(RFUContent.blockCapacitor.getRegistryName(), "inv2"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockCapacitor),  2, new ModelResourceLocation(RFUContent.blockCapacitor.getRegistryName(), "inv3"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockCapacitor),  3, new ModelResourceLocation(RFUContent.blockCapacitor.getRegistryName(), "inv4"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockCapacitor),  4, new ModelResourceLocation(RFUContent.blockCapacitor.getRegistryName(), "inv5"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockCapacitor),  5, new ModelResourceLocation(RFUContent.blockCapacitor.getRegistryName(), "inv6"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockCapacitor),  6, new ModelResourceLocation(RFUContent.blockCapacitor.getRegistryName(), "inv7"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockSwitch),     0, new ModelResourceLocation(RFUContent.blockSwitch.getRegistryName(),    "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockDiode),      0, new ModelResourceLocation(RFUContent.blockDiode.getRegistryName(),     "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockTransistor), 0, new ModelResourceLocation(RFUContent.blockTransistor.getRegistryName(),"inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockResistor),   0, new ModelResourceLocation(RFUContent.blockResistor.getRegistryName(),  "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockRFMeter),    0, new ModelResourceLocation(RFUContent.blockRFMeter.getRegistryName(),   "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RFUContent.blockInvisTess),  0, new ModelResourceLocation(RFUContent.blockInvisTess.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(RFUContent.itemMaterial,   0, new ModelResourceLocation(RFUContent.itemMaterial.getRegistryName(),   "hardenedGlassPane"));
        ModelLoader.setCustomModelResourceLocation(RFUContent.itemMaterial,   1, new ModelResourceLocation(RFUContent.itemMaterial.getRegistryName(),   "tessEmpty"));
        ModelLoader.setCustomModelResourceLocation(RFUContent.itemMaterial,   2, new ModelResourceLocation(RFUContent.itemMaterial.getRegistryName(),   "tessFull"));
        ModelLoader.setCustomModelResourceLocation(RFUContent.itemMaterial,   3, new ModelResourceLocation(RFUContent.itemMaterial.getRegistryName(),   "display"));
        ModelLoader.setCustomModelResourceLocation(RFUContent.itemMaterial,   4, new ModelResourceLocation(RFUContent.itemMaterial.getRegistryName(),   "redstonecircuit"));
        ModelLoader.setCustomModelResourceLocation(RFUContent.itemDialer,     0, new ModelResourceLocation(RFUContent.itemDialer.getRegistryName(),     "inventory"));
        ModelLoader.setCustomModelResourceLocation(RFUContent.itemMultimeter, 0, new ModelResourceLocation(RFUContent.itemMultimeter.getRegistryName(), "inventory"));
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
