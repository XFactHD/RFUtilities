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

import XFactHD.rfutilities.common.blocks.itemBlock.ItemBlockRFCapacitor;
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityCapacitor;
import XFactHD.rfutilities.common.items.ItemMultimeter;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;

@SuppressWarnings("deprecation")
public class BlockCapacitor extends BlockBaseRFU
{
    public static PropertyDirection ORIENTATION = PropertyDirection.create("facing", Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST));
    public static PropertyInteger TYPE = PropertyInteger.create("type", 1, 7);

    public BlockCapacitor()
    {
        super("blockCapacitor", Material.IRON, "");
        this.addItemBlock(new ItemBlockRFCapacitor(this));
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(ORIENTATION, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack)
    {
        TileEntity te = world.getTileEntity(pos);
        if (entity instanceof EntityPlayer && te instanceof TileEntityCapacitor)
        {
            ((TileEntityCapacitor) te).type = stack.getMetadata()+1;
            world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 0);
            te.markDirty();

        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityCapacitor && hand == EnumHand.MAIN_HAND && heldItem != null && heldItem.getItem() instanceof ItemMultimeter && !world.isRemote)
        {
            player.addChatComponentMessage(new TextComponentString(I18n.format("desc.rfutilities:stored.name") + " " + ((TileEntityCapacitor)te).getEnergyStored(EnumFacing.DOWN) + " " + I18n.format("desc.rfutilities:rf.name") + " / " + ((TileEntityCapacitor)te).getMaxEnergyStored(EnumFacing.DOWN) + " " + I18n.format("desc.rfutilities:rf.name")));
            return true;
        }
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ORIENTATION, TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (EnumFacing.getFront(meta) == EnumFacing.UP || EnumFacing.getFront(meta) == EnumFacing.DOWN)
        {
            return getDefaultState().withProperty(ORIENTATION, EnumFacing.NORTH);
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
        EnumFacing dir = state.getValue(ORIENTATION);
        TileEntity te = world.getTileEntity(pos);
        int cap = 0;
        if (te instanceof TileEntityCapacitor)
        {
            cap = ((TileEntityCapacitor)te).type;
        }
        if (cap == 0)
        {
            return state.withProperty(ORIENTATION, dir).withProperty(TYPE, 1);
        }
        return state.withProperty(ORIENTATION, dir).withProperty(TYPE, cap);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityCapacitor)
        {
            return new ItemStack(this, 1, ((TileEntityCapacitor)te).type-1);
        }
        return null;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityCapacitor();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}