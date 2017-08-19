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
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityResistor;
import XFactHD.rfutilities.common.utils.Reference;
//import cofh.thermalexpansion.item.tool.ItemMultimeter;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Arrays;

public class BlockResistor extends BlockBaseRFU
{
    public static PropertyDirection facing = PropertyDirection.create("facing", Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST));

    public BlockResistor()
    {
        super("blockResistor", Material.iron, ItemBlock.class, "");
        //setCreativeTab(RFUtilities.creativeTab);
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[]{facing});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing f;
        switch (meta)
        {
            case 0: f = EnumFacing.NORTH; break;
            case 1: f = EnumFacing.SOUTH; break;
            case 2: f = EnumFacing.WEST; break;
            case 3: f = EnumFacing.EAST; break;
            default: return super.getStateFromMeta(meta);
        }
        return getDefaultState().withProperty(facing, f);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(facing).getIndex();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityLivingBase, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityResistor /*&& player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemMultimeter*/ && !world.isRemote)
        {
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("desc.rfutilities:through.name") + " " + ((TileEntityResistor)te).throughput + " " + StatCollector.translateToLocal("desc.rfutilities:rftick.name")));
            return true;
        }
        else
        {
            if (!world.isRemote)
            {
                player.openGui(RFUtilities.instance, Reference.GUI_ID_RES, world, pos.getX(), pos.getY(), pos.getZ());
            }

        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityResistor();
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
