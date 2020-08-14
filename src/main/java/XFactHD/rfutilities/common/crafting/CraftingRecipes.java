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

package XFactHD.rfutilities.common.crafting;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.RFUContent;
import XFactHD.rfutilities.common.utils.MetaItemGetter;
import exter.substratum.fluid.SubstratumFluids;
import exter.substratum.item.SubstratumItems;
import exter.substratum.material.EnumMaterial;
import exter.substratum.material.EnumMaterialItem;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingRecipes
{
    private static ItemStack capTEBasic        = new ItemStack(RFUContent.blockCapacitor, 1, 0);
    private static ItemStack capTEHardened     = new ItemStack(RFUContent.blockCapacitor, 1, 1);
    private static ItemStack capTEReinforced   = new ItemStack(RFUContent.blockCapacitor, 1, 2);
    private static ItemStack capTEResonant     = new ItemStack(RFUContent.blockCapacitor, 1, 3);
    private static ItemStack capEIOBasic       = new ItemStack(RFUContent.blockCapacitor, 1, 4);
    private static ItemStack capEIODouble      = new ItemStack(RFUContent.blockCapacitor, 1, 5);
    private static ItemStack capEIOVibrant     = new ItemStack(RFUContent.blockCapacitor, 1, 6);

    private static ItemStack dialer            = new ItemStack(RFUContent.itemDialer, 1);

    private static ItemStack rfSwitch          = new ItemStack(RFUContent.blockSwitch);
    private static ItemStack rfResistor        = new ItemStack(RFUContent.blockResistor);
    private static ItemStack rfDiode           = new ItemStack(RFUContent.blockDiode);
    private static ItemStack invisTess         = new ItemStack(RFUContent.blockInvisTess);
    private static ItemStack rfMeter           = new ItemStack(RFUContent.blockRFMeter);
    private static ItemStack rfTransistor      = new ItemStack(RFUContent.blockTransistor);

    private static ItemStack hardenedGlassPane = new ItemStack(RFUContent.itemMaterial, 1, 0);
    private static ItemStack hardenedGlassPane4= new ItemStack(RFUContent.itemMaterial, 4, 0);
    private static ItemStack itemTessEmpty     = new ItemStack(RFUContent.itemMaterial, 1, 1);
    private static ItemStack itemTessFull      = new ItemStack(RFUContent.itemMaterial, 1, 2);
    private static ItemStack itemDisplay       = new ItemStack(RFUContent.itemMaterial, 1, 3);
    private static ItemStack redstoneCircuit   = new ItemStack(RFUContent.itemMaterial, 1, 4);

    private static ItemStack stoneSlab         = new ItemStack(Blocks.STONE_SLAB, 1, 0);

    private static ItemStack bucketEnder       = SubstratumItems.getStack(EnumMaterialItem.BUCKET_LIQUID, EnumMaterial.ENDERPEARL);

    public static void init()
    {
        addShapedOreRecipe(rfDiode,         "   ", "EQE", "SSS", 'E', "ingotElectrum", 'Q', "gemQuartz", 'S', stoneSlab);
        addShapedOreRecipe(rfResistor,      "   ", "ECE", "SSS", 'E', "ingotElectrum", 'C', "coal", 'S', stoneSlab);
        addShapedOreRecipe(rfSwitch,        "   ", "ELE", "SSS", 'E', "ingotElectrum", 'L', Blocks.LEVER, 'S', stoneSlab);
        addShapedOreRecipe(rfTransistor,    " R ", "ELE", "SSS", 'E', "ingotElectrum", 'L', Blocks.LEVER, 'R', "dustRedstone", 'S', stoneSlab);
        addShapedOreRecipe(itemDisplay,     "III", "IGI", "EEE", 'I', "ingotIron", 'G', "paneGlass", 'E', "nuggetElectrum");
        addShapedOreRecipe(redstoneCircuit, "ETE", "ERE", "ETE", 'E', "nuggetElectrum", 'R', "dustRedstone", 'T', Blocks.REDSTONE_TORCH);
        addShapedOreRecipe(rfMeter,         " D ", "ERE", "SSS", 'D', itemDisplay, 'E', "ingotElectrum", 'R', redstoneCircuit, 'S', stoneSlab);
        addShapedOreRecipe(itemTessEmpty,   "EGE", "GDG", "EGE", 'E', "nuggetEnderium", 'G', hardenedGlassPane, 'D', Items.DIAMOND);
        addShapedOreRecipe(itemTessFull,    " E ", " T ", "   ", 'T', itemTessEmpty, 'E', bucketEnder);
        addShapedOreRecipe(invisTess,       "BSB", "STS", "BSB", 'B', "ingotBronze", 'S', "ingotSilver", 'T', itemTessFull);
        addShapedOreRecipe(dialer,          " C ", "RBR", "III", 'C', "gearElectrum", 'R', "dustRedstone", 'B', Blocks.STONE_BUTTON, 'I', "ingotIron");

        if (OreDictionary.doesOreNameExist("ingotElectricalSteel"))
        {
            addShapedOreRecipe(rfDiode,      "   ", "EQE", "SSS", 'E', "ingotElectricalSteel", 'Q', "gemQuartz", 'S', stoneSlab);
            addShapedOreRecipe(rfResistor,   "   ", "ECE", "SSS", 'E', "ingotElectricalSteel", 'C', "coal", 'S', stoneSlab);
            addShapedOreRecipe(rfSwitch,     "   ", "ELE", "SSS", 'E', "ingotElectricalSteel", 'L', Blocks.LEVER, 'S', stoneSlab);
            addShapedOreRecipe(rfTransistor, " R ", "ELE", "SSS", 'E', "ingotElectricalSteel", 'L', Blocks.LEVER, 'R', "dustRedstone", 'S', stoneSlab);
            addShapedOreRecipe(itemDisplay,  "III", "IGI", "EEE", 'I', "ingotIron", 'G', "paneGlass", 'E', "nuggetElectricalSteel");
            addShapedOreRecipe(rfMeter,      " D ", "ERE", "SSS", 'D', itemDisplay, 'E', "ingotElectricalSteel", 'R', redstoneCircuit, 'S', stoneSlab);
        }

        if (OreDictionary.doesOreNameExist("blockGlassHardened"))
        {
            addShapedOreRecipe(hardenedGlassPane, "GGG", "GGG", "   ", 'G', "blockGlassHardened");
        }
        else
        {
            addShapedOreRecipe(hardenedGlassPane4, "LGL", "GOG", "LGL", 'L', "dustLead", 'G', "paneGlass", 'O', "obsidian");
        }

        if (RFUtilities.TE_LOADED)
        {
            addShapedOreRecipe(capTEBasic,      " C ", "ELE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEBasic, 'E', "ingotElectrum", 'L', "ingotLead");
            addShapedOreRecipe(capTEHardened,   " C ", "EIE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEHardened, 'E', "ingotElectrum", 'I', "ingotInvar");
            addShapedOreRecipe(capTEReinforced, " C ", "EEE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEReinforced, 'E', "ingotElectrum");
            addShapedOreRecipe(capTEResonant,   " C ", "ERE", "SSS", 'S', stoneSlab, 'C', MetaItemGetter.capTEResonant, 'E', "ingotElectrum", 'R', "ingotEnderium");
        }

        if (RFUtilities.EIO_LOADED)
        {
            addShapedOreRecipe(capEIOBasic,   " C ", "EIE", "SSS", 'C', MetaItemGetter.capEIOBasic, 'E',   "ingotElectricalSteel", 'S', stoneSlab, 'I', "ingotConductiveIron");
            addShapedOreRecipe(capEIODouble,  " C ", "EAE", "SSS", 'C', MetaItemGetter.capEIODouble, 'E',  "ingotElectricalSteel", 'S', stoneSlab, 'A', "ingotEnergeticAlloy");
            addShapedOreRecipe(capEIOVibrant, " C ", "EVE", "SSS", 'C', MetaItemGetter.capEIOVibrant, 'E', "ingotElectricalSteel", 'S', stoneSlab, 'V', "ingotVibrantAlloy");
        }
    }

    private static void addShapedOreRecipe(ItemStack output, Object... inputs)
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, inputs));
    }
}