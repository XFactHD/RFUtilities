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
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityInvisibleTesseract;
import XFactHD.rfutilities.common.utils.EventHandler;
import XFactHD.rfutilities.common.utils.LogHelper;
import XFactHD.rfutilities.common.utils.Reference;
import cofh.thermalexpansion.item.tool.ItemWrench;
import com.google.common.util.concurrent.UncheckedExecutionException;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockInvisibleTesseract extends BlockBaseRFU
{
    public BlockInvisibleTesseract()
    {
        super("blockInvisibleTesseract", Material.iron, 1, ItemBlock.class, "");
        //setCreativeTab(RFUtilities.creativeTab);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int m = MathHelper.floor_double((double) (entityLivingBase.rotationPitch * 4.0F / 360.0F) + 0.5D) & 3;

        if (m == 1){}
        if (m == 2){}
        LogHelper.info(m);
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
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
    {
        if (player.getCurrentEquippedItem() != null && !(player.getCurrentEquippedItem().getItem() instanceof ItemWrench))
        {
            //player.openGui(RFUtilities.instance, Reference.GUI_ID_TESS, world, x, y, z);
        }
        LogHelper.info(meta);
        return true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        if (EventHandler.isWrench || (world.getTileEntity(x, y, z) != null  && !((TileEntityInvisibleTesseract)world.getTileEntity(x, y, z)).blur))
        {
            switch (world.getBlockMetadata(x, y, z))
            {
                case 2: setBlockBounds(0F, .14F, .14F, .19F, .86F, .86F);
                case 3: setBlockBounds(.14F, .14F, 0F, .86F, .86F, .19F);
                case 4: setBlockBounds(1F, .14F, .86F, .81F, .86F, .14F);
                case 5: setBlockBounds(.14F, .14F, .81F, .86F, .86F, 1F);
                default: setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
            }
        }
        else
        {
            setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if (EventHandler.isWrench || (world.getTileEntity(x, y, z) != null  && !((TileEntityInvisibleTesseract)world.getTileEntity(x, y, z)).blur))
        {
            switch (world.getBlockMetadata(x, y, z))
            {
                case 2: return AxisAlignedBB.getBoundingBox((double)x, (double)y+.14D, (double)z+.14D, (double)x+.19D, (double)y+.86D, (double)z+.86D);
                case 3: return AxisAlignedBB.getBoundingBox((double)x+.14D, (double)y+.14D, (double)z, (double)x+.86D, (double)y+.86D, (double)z+.19D);
                case 4: return AxisAlignedBB.getBoundingBox((double)x+1, (double)y+.14D, (double)z+.86D, (double)x+.81D, (double)y+.86D, (double)z+.14D);
                case 5: return AxisAlignedBB.getBoundingBox((double)x+.14D, (double)y+.14D, (double)z+.81D, (double)x+.86D, (double)y+.86D, (double)z+1);
                default: return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)x+1, (double)y+1, (double)z+1);
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        switch (world.getBlockMetadata(x, y, z))
        {
            case 2: return AxisAlignedBB.getBoundingBox((double)x, (double)y+.13D, (double)z+.13D, (double)x+.2D, (double)y+.87D, (double)z+.87D);
            case 3: return AxisAlignedBB.getBoundingBox((double)x+.13D, (double)y+.13D, (double)z, (double)x+.87D, (double)y+.87D, (double)z+.2D);
            case 4: return AxisAlignedBB.getBoundingBox((double)x+1, (double)y+.13D, (double)z+.87D, (double)x+.8D, (double)y+.87D, (double)z+.13D);
            case 5: return AxisAlignedBB.getBoundingBox((double)x+.13D, (double)y+.13D, (double)z+.8D, (double)x+.87D, (double)y+.87D, (double)z+1);
            default: return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)x+1, (double)y+1, (double)z+1);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityInvisibleTesseract();
    }

    @Override
    public boolean canDismantle(EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer entityPlayer, World world, int x, int y, int z, boolean b)
    {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(new BlockInvisibleTesseract()));
        ArrayList list = new ArrayList<ItemStack>();
        list.add(stack);
        return list;
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