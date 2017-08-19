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

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityResistor;
import XFactHD.rfutilities.common.items.ItemMultimeter;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;

@SuppressWarnings("deprecation")
public class BlockResistor extends BlockBaseRFU
{
    public static PropertyDirection ORIENTATION = PropertyDirection.create("facing", Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST));

    public BlockResistor()
    {
        super("blockResistor", Material.IRON, "");
        this.addItemBlock(new ItemBlock(this));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ORIENTATION);
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
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(ORIENTATION, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityResistor && heldItem != null && heldItem.getItem() instanceof ItemMultimeter && !world.isRemote)
        {
            player.addChatComponentMessage(new TextComponentString(I18n.format("desc.rfutilities:through.name") + " " + ((TileEntityResistor)te).getThroughput() + " RF/t"));
            return true;
        }
        else if (!world.isRemote)
        {
            player.openGui(RFUtilities.instance, Reference.GUI_ID_RES, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityResistor();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
