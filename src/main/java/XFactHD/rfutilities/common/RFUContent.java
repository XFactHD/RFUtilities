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

import XFactHD.rfutilities.common.blocks.block.*;
import XFactHD.rfutilities.common.blocks.tileEntity.*;
import XFactHD.rfutilities.common.crafting.CraftingRecipes;
import XFactHD.rfutilities.common.items.*;
import XFactHD.rfutilities.common.utils.MetaItemGetter;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RFUContent
{
    public static BlockBaseRFU blockInvisTess;
    public static BlockBaseRFU blockCapacitor;
    public static BlockBaseRFU blockSwitch;
    public static BlockBaseRFU blockResistor;
    public static BlockBaseRFU blockDiode;
    public static BlockBaseRFU blockRFMeter;
    public static BlockBaseRFU blockTransistor;

    public static ItemBaseRFU itemMaterialTess;
    public static ItemBaseRFU itemMaterialDisplay;
    public static ItemBaseRFU itemMaterial;
    public static ItemBaseRFU itemDialer;

    public static void preInit()
    {
        itemMaterialDisplay = new ItemMaterialDisplay();

        blockCapacitor = new BlockRFCapacitor();
        blockSwitch = new BlockRFSwitch();
        blockResistor = new BlockRFResistor();
        blockDiode = new BlockDiode();
        blockRFMeter = new BlockRFMeter();
        //blockTransistor = new BlockTransistor();

        GameRegistry.registerTileEntity(TileEntityCapacitor.class, "tileCapacitor");
        GameRegistry.registerTileEntity(TileEntitySwitch.class, "tileSwitch");
        GameRegistry.registerTileEntity(TileEntityResistor.class, "tileResistor");
        GameRegistry.registerTileEntity(TileEntityDiode.class, "tileDiode");
        GameRegistry.registerTileEntity(TileEntityRFMeter.class, "tileRFMeter");
        //GameRegistry.registerTileEntity(TileEntityTransistor.class, "tileTransistor");

        if (Loader.isModLoaded("ThermalExpansion"))
        {
            itemDialer = new ItemDialer();
            itemMaterial = new ItemMaterial();
            itemMaterialTess = new ItemMaterialTess();
            blockInvisTess = new BlockInvisibleTesseract();
            GameRegistry.registerTileEntity(TileEntityInvisibleTesseract.class, "tileInvisTess");
        }
    }

    public static void init()
    {
        MetaItemGetter.init();
        CraftingRecipes.init();
    }

    public static void postInit()
    {

    }
}
