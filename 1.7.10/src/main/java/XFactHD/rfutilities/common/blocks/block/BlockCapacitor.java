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
import XFactHD.rfutilities.common.utils.LogHelper;
import cofh.thermalexpansion.item.tool.ItemMultimeter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCapacitor extends BlockBaseRFU
{
    public BlockCapacitor()
    {
        super("blockCapacitor", Material.iron, 1, ItemBlockRFCapacitor.class, "");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        TileEntity te = world.getTileEntity(x, y, z);
        if (entity instanceof EntityPlayer && te instanceof TileEntityCapacitor)
        {
            if ((stack.stackTagCompound) != null)
            {
                int type = stack.stackTagCompound.getInteger("type");
                ((TileEntityCapacitor)te).type = type;
                world.markBlockForUpdate(x, y, z);
                //LogHelper.info("Type on stack: " + type + "; Type on tile: " + ((TileEntityCapacitor)world.getTileEntity(x, y, z)).type);
                te.markDirty();
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCapacitor && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemMultimeter && !world.isRemote)
        {
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("desc.rfutilities:stored.name") + " " + ((TileEntityCapacitor)te).getEnergyStored(ForgeDirection.UNKNOWN) + " " + StatCollector.translateToLocal("desc.rfutilities:rf.name") + " / " + ((TileEntityCapacitor)te).getMaxEnergyStored(ForgeDirection.UNKNOWN) + " " + StatCollector.translateToLocal("desc.rfutilities:rf.name")));
            //LogHelper.info(((TileEntityCapacitor)world.getTileEntity(x, y, z)).type);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
        ItemStack stack = new ItemStack(this, 1);
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("type", ((TileEntityCapacitor)world.getTileEntity(x, y, z)).type);
        stack.setTagCompound(compound);
        return stack;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCapacitor();
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
}