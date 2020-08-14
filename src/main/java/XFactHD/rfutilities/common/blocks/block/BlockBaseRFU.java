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

package XFactHD.rfutilities.common.blocks.block;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public abstract class BlockBaseRFU extends Block implements ITileEntityProvider
{
    public String name;
    private String[] subNames;

    BlockBaseRFU(String name, Material mat, Class<? extends ItemBlock> itemblock, String... subNames)
    {
        super(mat);
        this.subNames = subNames;
        this.name = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MOD_ID + ":" + name);
        GameRegistry.registerBlock(this, itemblock, name);
        this.setCreativeTab(RFUtilities.creativeTab);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for(int i=0; i<subNames.length; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getLightOpacity()
    {
        return 0;
    }
}
