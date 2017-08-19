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

package XFactHD.rfutilities;

import XFactHD.rfutilities.common.CommonProxy;
import XFactHD.rfutilities.common.RFUContent;
import XFactHD.rfutilities.common.utils.LogHelper;
import XFactHD.rfutilities.common.utils.Reference;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class RFUtilities
{
    public static boolean devEnv = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static SimpleNetworkWrapper RFU_NET_WRAPPER;
    public static boolean TE_LOADED = false;
    public static boolean EIO_LOADED = false;
    public static boolean SUBSTRATUM_LOADED = false;
    public static boolean TESLA_LOADED = false;

    private static Comparator<ItemStack> tabSorter;

    @Mod.Instance(Reference.MOD_ID)
    public static RFUtilities instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    private static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Hello Minecraft!");
        //TE_LOADED = Loader.isModLoaded("ThermalExpansion");
        EIO_LOADED = Loader.isModLoaded("EnderIO");
        SUBSTRATUM_LOADED = Loader.isModLoaded("substratum");
        TESLA_LOADED = Loader.isModLoaded("tesla");
        RFU_NET_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("rfu");
        proxy.preInit(event);
        LogHelper.info("PreInit complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        initTabSorter();
        LogHelper.info("Init complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
        LogHelper.info("PostInit complete");
    }

    @SuppressWarnings("unchecked")
    private<F extends ItemStack> void initTabSorter()
    {
        List<Item> order = new ArrayList<>();
        order.add(Item.getItemFromBlock(RFUContent.blockCapacitor));
        order.add(Item.getItemFromBlock(RFUContent.blockSwitch));
        order.add(Item.getItemFromBlock(RFUContent.blockResistor));
        order.add(Item.getItemFromBlock(RFUContent.blockDiode));
        order.add(Item.getItemFromBlock(RFUContent.blockTransistor));
        order.add(Item.getItemFromBlock(RFUContent.blockRFMeter));
        order.add(Item.getItemFromBlock(RFUContent.blockInvisTess));
        order.add(RFUContent.itemDialer);
        order.add(RFUContent.itemMultimeter);
        order.add(RFUContent.itemMaterial);
        Function<F, Item> sorter = new Function<F, Item>()
        {
            @Nullable
            @Override
            public Item apply(F input)
            {
                return input.getItem();
            }
        };
        tabSorter = Ordering.explicit(order).onResultOf((Function<ItemStack, ? extends Item>) sorter);
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
            return new ItemStack(RFUContent.blockCapacitor, 1, 0);
        }

        @Override
        public void displayAllRelevantItems(List<ItemStack> list)
        {
            super.displayAllRelevantItems(list);
            Collections.sort(list, tabSorter);
        }
    };
}