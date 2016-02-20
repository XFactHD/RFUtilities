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

package XFactHD.rfutilities.common.crafting;

import XFactHD.rfutilities.common.RFUContent;
import XFactHD.rfutilities.common.utils.MetaItemGetter;
import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.thermalfoundation.fluid.TFFluids;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class CraftingRecipes
{
    static ItemStack capTEBasic        = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capTEHardened     = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capTEReinforced   = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capTEResonant     = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capEIOBasic       = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capEIODouble      = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capEIOVibrant     = new ItemStack(RFUContent.blockCapacitor, 1);

    static ItemStack rfSwitch          = new ItemStack(RFUContent.blockSwitch);
    static ItemStack rfResistor        = new ItemStack(RFUContent.blockResistor);
    static ItemStack rfDiode           = new ItemStack(RFUContent.blockDiode);
    static ItemStack invisTess         = new ItemStack(RFUContent.blockInvisTess);
    static ItemStack rfMeter           = new ItemStack(RFUContent.blockRFMeter);

    static ItemStack itemTessEmpty     = new ItemStack(RFUContent.itemMaterial, 1, 0);
    static ItemStack itemTessFull      = new ItemStack(RFUContent.itemMaterial, 1, 1);
    static ItemStack itemDisplay       = new ItemStack(RFUContent.itemMaterial, 1, 2);
    static ItemStack hardenedGlassPane = new ItemStack(RFUContent.itemMaterial, 1, 3);

    static ItemStack stoneSlab         = new ItemStack(Blocks.stone_slab, 1, 0);
    static ItemStack coal              = new ItemStack(Items.coal, 1, 0);
    static ItemStack lever             = new ItemStack(Blocks.lever, 1);
    static ItemStack redstone          = new ItemStack(Items.redstone);
    static ItemStack glassPane         = new ItemStack(Blocks.glass_pane, 1);
    static ItemStack ingotIron         = new ItemStack(Items.iron_ingot, 1);
    static ItemStack repeater          = new ItemStack(Items.repeater, 1);
    static ItemStack netherQuartz      = new ItemStack(Items.quartz, 1);
    static ItemStack diamond           = new ItemStack(Items.diamond);

    static FluidStack fluidEnder       = new FluidStack(TFFluids.fluidEnder, 1000);

    static NBTTagCompound compoundCapTEBasic = new NBTTagCompound();
    static NBTTagCompound compoundCapTEHardened = new NBTTagCompound();
    static NBTTagCompound compoundCapTEReinforced = new NBTTagCompound();
    static NBTTagCompound compoundCapTEResonant = new NBTTagCompound();
    static NBTTagCompound compoundCapEIOBasic = new NBTTagCompound();
    static NBTTagCompound compoundCapEIODouble = new NBTTagCompound();
    static NBTTagCompound compoundCapEIOVibrant = new NBTTagCompound();

    public static void init()
    {
        compoundCapTEBasic.setInteger("type", 1);
        compoundCapTEHardened.setInteger("type", 2);
        compoundCapTEReinforced.setInteger("type", 3);
        compoundCapTEResonant.setInteger("type", 4);
        compoundCapEIOBasic.setInteger("type", 5);
        compoundCapEIODouble.setInteger("type", 6);
        compoundCapEIOVibrant.setInteger("type", 7);

        capTEBasic.setTagCompound(compoundCapTEBasic);
        capTEHardened.setTagCompound(compoundCapTEHardened);
        capTEReinforced.setTagCompound(compoundCapTEReinforced);
        capTEResonant.setTagCompound(compoundCapTEResonant);
        capEIOBasic.setTagCompound(compoundCapEIOBasic);
        capEIODouble.setTagCompound(compoundCapEIODouble);
        capEIOVibrant.setTagCompound(compoundCapEIOVibrant);

        GameRegistry.addShapedRecipe(rfDiode,       "   ", "EQE", "SSS", 'E', MetaItemGetter.ingotElectrum, 'Q', netherQuartz, 'S', stoneSlab);
        GameRegistry.addShapedRecipe(rfResistor,    "   ", "ECE", "SSS", 'E', MetaItemGetter.ingotElectrum, 'C', coal, 'S', stoneSlab);
        GameRegistry.addShapedRecipe(rfSwitch,      " L ", "ERE", "SSS", 'E', MetaItemGetter.ingotElectrum, 'L', lever, 'R', redstone, 'S', stoneSlab);
        GameRegistry.addShapedRecipe(itemDisplay,   "III", "IGI", "EEE", 'I', ingotIron, 'G', glassPane, 'E', MetaItemGetter.nuggetElectrum);
        GameRegistry.addShapedRecipe(rfMeter,       " D ", "ERE", "SSS", 'D', itemDisplay, 'E', MetaItemGetter.ingotElectrum, 'R', repeater, 'S', stoneSlab);
        GameRegistry.addShapedRecipe(hardenedGlassPane, "GGG", "GGG", "   ", 'G', MetaItemGetter.hardenedGlass);
        GameRegistry.addShapedRecipe(itemTessEmpty, "EGE", "GDG", "EGE", 'E', MetaItemGetter.nuggetEnderium, 'G', hardenedGlassPane, 'D', diamond);
        GameRegistry.addShapedRecipe(invisTess,     "BSB", "STS", "BSB", 'B', MetaItemGetter.ingotBronze, 'S', MetaItemGetter.ingotSilver, 'T', itemTessFull);

        if (Loader.isModLoaded("ThermalExpansion"))
        {
            GameRegistry.addShapedRecipe(capTEBasic,      " C ", "ELE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEBasic, 'E', MetaItemGetter.ingotElectrum, 'L', MetaItemGetter.ingotLead);
            GameRegistry.addShapedRecipe(capTEHardened,   " C ", "EIE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEHardened, 'E', MetaItemGetter.ingotElectrum, 'I', MetaItemGetter.ingotInvar);
            GameRegistry.addShapedRecipe(capTEReinforced, " C ", "EEE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEReinforced, 'E', MetaItemGetter.ingotElectrum);
            GameRegistry.addShapedRecipe(capTEResonant,   " C ", "ERE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEResonant, 'E', MetaItemGetter.ingotElectrum, 'R', MetaItemGetter.ingotEnderium);
            ThermalExpansionHelper.addTransposerFill(16000, itemTessEmpty, itemTessFull, fluidEnder, false);
        }
        else
        {
            GameRegistry.addShapedRecipe(itemTessFull,  " P ", "PTP", " P ", 'P', Items.ender_pearl, 'T', itemTessEmpty);
        }

        if (Loader.isModLoaded("EnderIO"))
        {
            GameRegistry.addShapedRecipe(capEIOBasic,   " C ", "EIE", "SSS", 'C', MetaItemGetter.capEIOBasic, 'E',   MetaItemGetter.ingotElectricalSteel, 'S', stoneSlab, 'I', MetaItemGetter.ingotConductiveIron);
            GameRegistry.addShapedRecipe(capEIODouble,  " C ", "EAE", "SSS", 'C', MetaItemGetter.capEIODouble, 'E',  MetaItemGetter.ingotElectricalSteel, 'S', stoneSlab, 'A', MetaItemGetter.ingotEnergeticAlloy);
            GameRegistry.addShapedRecipe(capEIOVibrant, " C ", "EVE", "SSS", 'C', MetaItemGetter.capEIOVibrant, 'E', MetaItemGetter.ingotElectricalSteel, 'S', stoneSlab, 'V', MetaItemGetter.ingotVibrantAlloy);
        }
    }
}
