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
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingRecipes
{
    static ItemStack capTEBasic        = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capTEHardened     = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capTEReinforced   = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capTEResonant     = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capEIOBasic       = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capEIODouble      = new ItemStack(RFUContent.blockCapacitor, 1);
    static ItemStack capEIOVibrant     = new ItemStack(RFUContent.blockCapacitor, 1);

    static ItemStack dialer            = new ItemStack(RFUContent.itemDialer, 1);

    static ItemStack rfSwitch          = new ItemStack(RFUContent.blockSwitch);
    static ItemStack rfResistor        = new ItemStack(RFUContent.blockResistor);
    static ItemStack rfDiode           = new ItemStack(RFUContent.blockDiode);
    static ItemStack invisTess         = new ItemStack(RFUContent.blockInvisTess);
    static ItemStack rfMeter           = new ItemStack(RFUContent.blockRFMeter);

    static ItemStack itemTessEmpty     = new ItemStack(RFUContent.itemMaterialTess, 1, 0);
    static ItemStack itemTessFull      = new ItemStack(RFUContent.itemMaterialTess, 1, 1);
    static ItemStack itemDisplay       = new ItemStack(RFUContent.itemMaterialDisplay, 1, 0);
    static ItemStack hardenedGlassPane = new ItemStack(RFUContent.itemMaterial, 1, 0);

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

        GameRegistry.addRecipe(new ShapedOreRecipe(rfDiode,           "   ", "EQE", "SSS", 'E', "ingotElectrum", 'Q', netherQuartz, 'S', stoneSlab));
        GameRegistry.addRecipe(new ShapedOreRecipe(rfResistor,        "   ", "ECE", "SSS", 'E', "ingotElectrum", 'C', coal, 'S', stoneSlab));
        GameRegistry.addRecipe(new ShapedOreRecipe(rfSwitch,          " L ", "ERE", "SSS", 'E', "ingotElectrum", 'L', lever, 'R', redstone, 'S', stoneSlab));
        GameRegistry.addRecipe(new ShapedOreRecipe(itemDisplay,       "III", "IGI", "EEE", 'I', ingotIron, 'G', glassPane, 'E', "nuggetElectrum"));
        GameRegistry.addRecipe(new ShapedOreRecipe(rfMeter,           " D ", "ERE", "SSS", 'D', itemDisplay, 'E', "ingotElectrum", 'R', repeater, 'S', stoneSlab));

        if (Loader.isModLoaded("ThermalExpansion"))
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(capTEBasic,      " C ", "ELE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEBasic, 'E', "ingotElectrum", 'L', "ingotLead"));
            GameRegistry.addRecipe(new ShapedOreRecipe(capTEHardened,   " C ", "EIE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEHardened, 'E', "ingotElectrum", 'I', "ingotInvar"));
            GameRegistry.addRecipe(new ShapedOreRecipe(capTEReinforced, " C ", "EEE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEReinforced, 'E', "ingotElectrum"));
            GameRegistry.addRecipe(new ShapedOreRecipe(capTEResonant,   " C ", "ERE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEResonant, 'E', "ingotElectrum", 'R', "ingotEnderium"));
            GameRegistry.addRecipe(new ShapedOreRecipe(hardenedGlassPane, "GGG", "GGG", "   ", 'G', "blockGlassHardened"));
            GameRegistry.addRecipe(new ShapedOreRecipe(itemTessEmpty,     "EGE", "GDG", "EGE", 'E', "nuggetEnderium", 'G', hardenedGlassPane, 'D', diamond));
            GameRegistry.addRecipe(new ShapedOreRecipe(invisTess,         "BSB", "STS", "BSB", 'B', "ingotBronze", 'S', "ingotSilver", 'T', itemTessFull));
            GameRegistry.addRecipe(new ShapedOreRecipe(dialer,            " C ", "RBR", "III", 'C', MetaItemGetter.coil, 'R', redstone, 'B', Blocks.stone_button, 'I', ingotIron));
            ThermalExpansionHelper.addTransposerFill(16000, itemTessEmpty, itemTessFull, fluidEnder, false);
        }

        if (Loader.isModLoaded("EnderIO"))
        {
            GameRegistry.addShapedRecipe(capEIOBasic,   " C ", "EIE", "SSS", 'C', MetaItemGetter.capEIOBasic, 'E',   MetaItemGetter.ingotElectricalSteel, 'S', stoneSlab, 'I', MetaItemGetter.ingotConductiveIron);
            GameRegistry.addShapedRecipe(capEIODouble,  " C ", "EAE", "SSS", 'C', MetaItemGetter.capEIODouble, 'E',  MetaItemGetter.ingotElectricalSteel, 'S', stoneSlab, 'A', MetaItemGetter.ingotEnergeticAlloy);
            GameRegistry.addShapedRecipe(capEIOVibrant, " C ", "EVE", "SSS", 'C', MetaItemGetter.capEIOVibrant, 'E', MetaItemGetter.ingotElectricalSteel, 'S', stoneSlab, 'V', MetaItemGetter.ingotVibrantAlloy);
        }
    }
}
