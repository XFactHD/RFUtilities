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
import XFactHD.rfutilities.common.items.ItemDialer;
import XFactHD.rfutilities.common.utils.EventHandler;
import cofh.thermalexpansion.item.tool.ItemMultimeter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockInvisibleTesseract extends BlockBaseRFU
{
    public BlockInvisibleTesseract()
    {
        super("blockInvisibleTesseract", Material.iron, 1, ItemBlock.class, "");
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int m = MathHelper.floor_double((double) (entityLivingBase.rotationPitch * 4.0F / 360.0F) + 0.5D) & 3;
        //LogHelper.info(m);

        if (m == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
        else if (m == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }
        else if (m == 0)
        {
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
        //LogHelper.info("Meta: " + world.getBlockMetadata(x, y, z) + ", l: " + l + ", m: " + m);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntityInvisibleTesseract tesseract = ((TileEntityInvisibleTesseract)world.getTileEntity(x, y, z));
            ItemStack equipped = player.getCurrentEquippedItem();

            if(equipped != null && equipped.getItem() instanceof ItemDialer)
            {
                if (equipped.hasTagCompound())
                {
                    int senderX = equipped.stackTagCompound.getInteger("senderX");
                    int senderY = equipped.stackTagCompound.getInteger("senderY");
                    int senderZ = equipped.stackTagCompound.getInteger("senderZ");

                    if (!equipped.stackTagCompound.getBoolean("modeDial"))
                    {
                        tesseract.clearFrequency();
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:clearedTess.name")));
                    }
                    else
                    {
                        TileEntity teTess = world.getTileEntity(senderX, senderY, senderZ);
                        if (teTess instanceof TileEntityInvisibleTesseract)
                        {
                            TileEntityInvisibleTesseract sender = ((TileEntityInvisibleTesseract)teTess);
                            if (!(sender == tesseract))
                            {
                                if (tesseract.dialedSender != null && tesseract.dialedSender == sender)
                                {
                                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:alreadyDialed.name")));
                                }
                                else
                                {
                                    tesseract.setActive(true);
                                    tesseract.setSender(false);
                                    tesseract.setDialedSender(sender);
                                    sender.addReceiver(tesseract);
                                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:dialedToSender.name")));
                                }
                            }
                            else
                            {
                                player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + StatCollector.translateToLocal("chat.rfutilities:cantDialSame.name")));
                            }
                        }
                        else
                        {
                            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:missingSender.name")));
                            equipped.setTagCompound(null);
                        }
                    }

                }
                else
                {
                    NBTTagCompound compound = new NBTTagCompound();
                    int teX = tesseract.xCoord;
                    int teY = tesseract.yCoord;
                    int teZ = tesseract.zCoord;
                    compound.setInteger("senderX", teX);
                    compound.setInteger("senderY", teY);
                    compound.setInteger("senderZ", teZ);
                    equipped.setTagCompound(compound);
                    if (!tesseract.isActive)
                    {
                        tesseract.setActive(true);
                    }
                    tesseract.setSender(true);
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:dialedToDialer.name")));
                }
            }
            else if (equipped != null && RFUtilities.TE_LOADED && equipped.getItem() instanceof ItemMultimeter)
            {
                if (!player.isSneaking())
                {
                    if (tesseract.isActive && tesseract.isSender)
                    {
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:isSender.name")));
                    }
                    else if (tesseract.isActive)
                    {
                        int dialedX = tesseract.dialedSender.xCoord;
                        int dialedY = tesseract.dialedSender.yCoord;
                        int dialedZ = tesseract.dialedSender.zCoord;
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("desc.rfutilities:dialedTo.name") + " " + StatCollector.translateToLocal("desc.rfutilities:tessAt.name") + " X=" + dialedX + ", Y=" + dialedY + ", Z=" + dialedZ + "."));
                    }
                    else
                    {
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:undialed.name")));
                    }
                }
                else
                {
                    tesseract.hidden = !tesseract.hidden;
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:hidden_" + Boolean.toString(tesseract.hidden) + ".name")));
                }
            }
            else
            {
                //tesseract.clicked();
            }
        }
        return true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        if (EventHandler.canItemShowTess || (world.getTileEntity(x, y, z) instanceof TileEntityInvisibleTesseract  && !((TileEntityInvisibleTesseract)world.getTileEntity(x, y, z)).hidden))
        {
            switch (world.getBlockMetadata(x, y, z))
            {
                case 0: setBlockBounds(0F, .14F, .14F, .19F, .86F, .86F);
                case 1: setBlockBounds(0F, .14F, .14F, .19F, .86F, .86F);
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
        if (EventHandler.canItemShowTess || (world.getTileEntity(x, y, z) instanceof TileEntityInvisibleTesseract  && !((TileEntityInvisibleTesseract)world.getTileEntity(x, y, z)).hidden))
        {
            switch (world.getBlockMetadata(x, y, z))
            {
                case 0: return AxisAlignedBB.getBoundingBox((double)x+.13D, (double)y, (double)z+.13D, (double)x+.87D, (double)y+.19D, (double)z+.87D);
                case 1: return AxisAlignedBB.getBoundingBox((double)x+.13D, (double)y+.81D, (double)z+.13D, (double)x+.87D, (double)y+1D, (double)z+.87D);
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
            case 0: return AxisAlignedBB.getBoundingBox((double)x+.13D, (double)y,      (double)z+.13D, (double)x+.87D, (double)y+.19D, (double)z+.87D);
            case 1: return AxisAlignedBB.getBoundingBox((double)x+.13D, (double)y+.81D, (double)z+.13D, (double)x+.87D, (double)y  +1D, (double)z+.87D);
            case 2: return AxisAlignedBB.getBoundingBox((double)x, (double)y+.13D, (double)z+.13D, (double)x+.2D, (double)y+.87D, (double)z+.87D);
            case 3: return AxisAlignedBB.getBoundingBox((double)x+.13D, (double)y+.13D, (double)z, (double)x+.87D, (double)y+.87D, (double)z+.2D);
            case 4: return AxisAlignedBB.getBoundingBox((double)x+1, (double)y+.13D, (double)z+.87D, (double)x+.8D, (double)y+.87D, (double)z+.13D);
            case 5: return AxisAlignedBB.getBoundingBox((double)x+.13D, (double)y+.13D, (double)z+.8D, (double)x+.87D, (double)y+.87D, (double)z+1);
            default: return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)x+1, (double)y+1, (double)z+1);
        }
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec)
    {
        return super.collisionRayTrace(world, x, y, z, startVec, endVec);
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