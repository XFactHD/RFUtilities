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

package XFactHD.rfutilities.common.blocks.block;

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntitySwitch;
//import cofh.thermalexpansion.item.tool.ItemWrench;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;

@SuppressWarnings("deprecation")
public class BlockSwitch extends BlockBaseRFU
{
    public static PropertyDirection ORIENTATION = PropertyDirection.create("facing", Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST));
    private static PropertyBool status = PropertyBool.create("status");

    public BlockSwitch()
    {
        super("blockSwitch", Material.IRON, "");
        this.addItemBlock(new ItemBlock(this));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ORIENTATION, status);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta == 0 || meta == 1)
        {
            meta = EnumFacing.NORTH.getIndex();
        }
        return getDefaultState().withProperty(ORIENTATION, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ORIENTATION).getIndex();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity te = world.getTileEntity(pos);
        boolean on = false;
        if (te instanceof TileEntitySwitch)
        {
            on = ((TileEntitySwitch)te).getIsOn();
        }
        return state.withProperty(status, on);
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(ORIENTATION, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        //if (heldItem == null || !(heldItem.getItem() instanceof ItemWrench))
        //{
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntitySwitch)
            {
                ((TileEntitySwitch)te).setIsOn(!((TileEntitySwitch)te).getIsOn());
                world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 1);
                world.markBlockRangeForRenderUpdate(pos, pos);
                world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, ((TileEntitySwitch)te).getIsOn() ? 0.6F : 0.5F);
            }
        //}
        //else if (heldItem != null && heldItem.getItem() instanceof ItemWrench)
        //{
        //    state.cycleProperty(ORIENTATION);
        //}
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntitySwitch();
    }
}