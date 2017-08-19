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

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityRFMeter;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Arrays;

public class BlockRFMeter extends BlockBaseRFU
{
    public static PropertyDirection facing = PropertyDirection.create("facing", Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST));

    public BlockRFMeter()
    {
        super("blockRFMeter", Material.iron, ItemBlock.class, "");
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            world.setBlockState(pos, state.withProperty(facing, EnumFacing.NORTH), 2);
        }

        if (l == 1)
        {
            world.setBlockState(pos, state.withProperty(facing, EnumFacing.EAST), 2);
        }

        if (l == 2)
        {
            world.setBlockState(pos, state.withProperty(facing, EnumFacing.SOUTH), 2);
        }

        if (l == 3)
        {
            world.setBlockState(pos, state.withProperty(facing, EnumFacing.WEST), 2);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityRFMeter();
    }

    @Override
    public int getRenderType()
    {
        return 3;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
