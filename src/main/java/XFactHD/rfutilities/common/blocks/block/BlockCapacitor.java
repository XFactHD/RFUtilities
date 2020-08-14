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

import XFactHD.rfutilities.common.blocks.itemBlock.ItemBlockRFCapacitor;
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityCapacitor;
//import cofh.thermalexpansion.item.tool.ItemMultimeter;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Arrays;

public class BlockCapacitor extends BlockBaseRFU
{
    public static PropertyDirection ORIENTATION = PropertyDirection.create("facing", Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST));
    private static PropertyInteger TYPE = PropertyInteger.create("type", 1, 7);

    public BlockCapacitor()
    {
        super("blockCapacitor", Material.iron, ItemBlockRFCapacitor.class, "");
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            world.setBlockState(pos, state.withProperty(ORIENTATION, EnumFacing.NORTH), 2);
        }

        if (l == 1)
        {
            world.setBlockState(pos, state.withProperty(ORIENTATION, EnumFacing.EAST), 2);
        }

        if (l == 2)
        {
            world.setBlockState(pos, state.withProperty(ORIENTATION, EnumFacing.SOUTH), 2);
        }

        if (l == 3)
        {
            world.setBlockState(pos, state.withProperty(ORIENTATION, EnumFacing.WEST), 2);
        }

        TileEntity te = world.getTileEntity(pos);
        if (entity instanceof EntityPlayer && te instanceof TileEntityCapacitor)
        {
            if ((stack.getTagCompound()) != null)
            {
                ((TileEntityCapacitor)te).type = stack.getTagCompound().getInteger("type");
                world.markBlockForUpdate(pos);
                //LogHelper.info("Type on stack: " + stack.getTagCompound().getInteger("type") + "; Type on tile: " + ((TileEntityCapacitor)world.getTileEntity(pos)).type);
                te.markDirty();
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityCapacitor /*&& player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemMultimeter*/ && !world.isRemote)
        {
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("desc.rfutilities:stored.name") + " " + ((TileEntityCapacitor)te).getEnergyStored(EnumFacing.DOWN) + " " + StatCollector.translateToLocal("desc.rfutilities:rf.name") + " / " + ((TileEntityCapacitor)te).getMaxEnergyStored(EnumFacing.DOWN) + " " + StatCollector.translateToLocal("desc.rfutilities:rf.name")));
            //LogHelper.info(((TileEntityCapacitor)world.getTileEntity(x, y, z)).type);
            return true;
        }
        return false;
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, ORIENTATION, TYPE);
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
        int cap = 1;
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
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
    {
        ItemStack stack = new ItemStack(this, 1);
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("TYPE", ((TileEntityCapacitor)world.getTileEntity(pos)).type);
        stack.setTagCompound(compound);
        return stack;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCapacitor();
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}