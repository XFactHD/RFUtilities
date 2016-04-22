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

package XFactHD.rfutilities;

import XFactHD.rfutilities.common.CommonProxy;
import XFactHD.rfutilities.common.RFUContent;
import XFactHD.rfutilities.common.utils.LogHelper;
import XFactHD.rfutilities.common.utils.Reference;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class RFUtilities
{
    public static SimpleNetworkWrapper RFU_NET_WRAPPER;
    public static boolean TE_LOADED = false;

    @Mod.Instance(Reference.MOD_ID)
    public static RFUtilities instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    private static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Hello Minecraft!");
        TE_LOADED = Loader.isModLoaded("ThermalExpansion");
        RFU_NET_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("rfu");
        proxy.preInit(event);
        LogHelper.info("PreInit complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        LogHelper.info("Init complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
        LogHelper.info("PostInit complete");
    }

    public static CreativeTabs creativeTab = new CreativeTabs(Reference.MOD_ID)
    {
        @Override
        public Item getTabIconItem()
        {
            return null;
        }

        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(Item.getItemFromBlock(RFUContent.blockCapacitor), 1, 0);
        }
    };
}
