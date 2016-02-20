/*  Copyright (C) <2015>  <XFactHD, DrakoAlcarus>

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

package XFactHD.rfutilities.common.utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MetaItemGetter
{
    public static ItemStack ingotLead;
    public static ItemStack ingotInvar;
    public static ItemStack ingotElectrum;
    public static ItemStack ingotEnderium;
    public static ItemStack ingotBronze;
    public static ItemStack ingotSilver;

    public static ItemStack nuggetElectrum;
    public static ItemStack nuggetEnderium;

    public static ItemStack hardenedGlass;

    public static ItemStack ingotConductiveIron;
    public static ItemStack ingotEnergeticAlloy;
    public static ItemStack ingotVibrantAlloy;
    public static ItemStack ingotElectricalSteel;

    public static ItemStack capTEBasic;
    public static ItemStack capTEHardened;
    public static ItemStack capTEReinforced;
    public static ItemStack capTEResonant;
    public static ItemStack capEIOBasic;
    public static ItemStack capEIODouble;
    public static ItemStack capEIOVibrant;

    public static void init()
    {
        if (Loader.isModLoaded("ThermalExpansion"))
        {
            Item capItemTE = GameRegistry.findItem("ThermalExpansion", "capacitor");
            capTEBasic = new ItemStack(capItemTE, 1, 2);
            capTEHardened = new ItemStack(capItemTE, 1, 3);
            capTEReinforced = new ItemStack(capItemTE, 1, 4);
            capTEResonant = new ItemStack(capItemTE, 1, 5);

            ingotLead = OreDictionary.getOres("ingotLead").get(0);
            ingotInvar = OreDictionary.getOres("ingotInvar").get(0);
            ingotElectrum = OreDictionary.getOres("ingotElectrum").get(0);
            ingotEnderium = OreDictionary.getOres("ingotEnderium").get(0);
            ingotBronze = OreDictionary.getOres("ingotBronze").get(0);
            ingotSilver = OreDictionary.getOres("ingotSilver").get(0);

            hardenedGlass = OreDictionary.getOres("blockGlassHardened").get(0);

            nuggetElectrum = OreDictionary.getOres("nuggetElectrum").get(0);
            nuggetEnderium = OreDictionary.getOres("nuggetEnderium").get(0);
        }

        if (Loader.isModLoaded("EnderIO"))
        {
            Item capItemEIO = GameRegistry.findItem("EnderIO", "itemBasicCapacitor");
            capEIOBasic = new ItemStack(capItemEIO, 1, 0);
            capEIODouble = new ItemStack(capItemEIO, 1, 1);
            capEIOVibrant = new ItemStack(capItemEIO, 1, 2);

            Item alloyItem = GameRegistry.findItem("EnderIO", "itemAlloy");
            ingotElectricalSteel = new ItemStack(alloyItem, 1, 0);
            ingotConductiveIron = new ItemStack(alloyItem, 1, 4);
            ingotEnergeticAlloy = new ItemStack(alloyItem, 1, 1);
            ingotVibrantAlloy = new ItemStack(alloyItem, 1, 2);
        }
    }
}