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
//import cofh.thermalexpansion.item.tool.ItemMultimeter;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInvisibleTesseract extends BlockBaseRFU
{
    public static PropertyDirection facing = PropertyDirection.create("facing");

    public BlockInvisibleTesseract()
    {
        super("blockInvisibleTesseract", Material.iron, ItemBlock.class, "");
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int m = MathHelper.floor_double((double) (entity.rotationPitch * 4.0F / 360.0F) + 0.5D) & 3;

        if (m == 1)
        {
            world.setBlockState(pos, state.withProperty(facing, EnumFacing.DOWN), 2);
        }
        else if (m == 3)
        {
            world.setBlockState(pos, state.withProperty(facing, EnumFacing.UP), 2);
        }
        else if (m == 0)
        {
            if (l == 0)
            {
                world.setBlockState(pos, state.withProperty(facing, EnumFacing.SOUTH), 2);
            }

            if (l == 1)
            {
                world.setBlockState(pos, state.withProperty(facing, EnumFacing.WEST), 2);
            }

            if (l == 2)
            {
                world.setBlockState(pos, state.withProperty(facing, EnumFacing.NORTH), 2);
            }

            if (l == 3)
            {
                world.setBlockState(pos, state.withProperty(facing, EnumFacing.EAST), 2);
            }
        }
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, facing);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing f;
        switch (meta)
        {
            case 0: f = EnumFacing.DOWN; break;
            case 1: f = EnumFacing.UP; break;
            case 2: f = EnumFacing.NORTH; break;
            case 3: f = EnumFacing.SOUTH; break;
            case 4: f = EnumFacing.WEST; break;
            case 5: f = EnumFacing.EAST; break;
            default: return super.getStateFromMeta(meta);
        }
        return getDefaultState().withProperty(facing, f);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumFacing f = state.getValue(facing);
        return f.getIndex();
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntityInvisibleTesseract tesseract = ((TileEntityInvisibleTesseract)world.getTileEntity(pos));
            ItemStack equipped = player.getCurrentEquippedItem();

            if(equipped != null && equipped.getItem() instanceof ItemDialer)
            {
                if (equipped.hasTagCompound())
                {
                    int senderX = equipped.getTagCompound().getInteger("senderX");
                    int senderY = equipped.getTagCompound().getInteger("senderY");
                    int senderZ = equipped.getTagCompound().getInteger("senderZ");

                    if (!equipped.getTagCompound().getBoolean("modeDial"))
                    {
                        tesseract.clearFrequency();
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:clearedTess.name")));
                    }
                    else
                    {
                        TileEntity teTess = world.getTileEntity(new BlockPos(senderX, senderY, senderZ));
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
                    int teX = tesseract.getPos().getX();
                    int teY = tesseract.getPos().getY();
                    int teZ = tesseract.getPos().getZ();
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
            else if (equipped != null && RFUtilities.TE_LOADED /*&& equipped.getItem() instanceof ItemMultimeter*/)
            {
                if (!player.isSneaking())
                {
                    if (tesseract.isActive && tesseract.isSender)
                    {
                        player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:isSender.name")));
                    }
                    else if (tesseract.isActive)
                    {
                        int dialedX = tesseract.dialedSender.getPos().getX();
                        int dialedY = tesseract.dialedSender.getPos().getY();
                        int dialedZ = tesseract.dialedSender.getPos().getZ();
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
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
    {
        if (EventHandler.canItemShowTess || (world.getTileEntity(pos) instanceof TileEntityInvisibleTesseract  && !((TileEntityInvisibleTesseract)world.getTileEntity(pos)).hidden))
        {
            switch (world.getBlockState(pos).getValue(facing))
            {
                case DOWN:  setBlockBounds(.13F,   0F, .13F, .87F, .19F, .87F);
                case UP:    setBlockBounds(.13F, .81F, .13F, .87F,   1F, .87F);
                case NORTH: setBlockBounds(.14F, .14F,   0F, .86F, .86F, .19F);
                case SOUTH: setBlockBounds(.14F, .14F, .81F, .86F, .86F,   1F);
                case WEST:  setBlockBounds(  0F, .14F, .14F, .19F, .86F, .86F);
                case EAST:  setBlockBounds(  1F, .14F, .86F, .81F, .86F, .14F);
                default:    setBlockBounds(  0F,   0F,   0F,   1F,   1F,   1F);
            }
        }
        else
        {
            setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
    {
        if (EventHandler.canItemShowTess || (world.getTileEntity(pos) instanceof TileEntityInvisibleTesseract  && !((TileEntityInvisibleTesseract)world.getTileEntity(pos)).hidden))
        {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            switch (world.getBlockState(pos).getValue(facing))
            {
                case DOWN:  return AxisAlignedBB.fromBounds((double)x+.13D, (double)y,      (double)z+.13D, (double)x+.87D, (double)y+.19D, (double)z+.87D);
                case UP:    return AxisAlignedBB.fromBounds((double)x+.13D, (double)y+.81D, (double)z+.13D, (double)x+.87D, (double)y+1D,   (double)z+.87D);
                case NORTH: return AxisAlignedBB.fromBounds((double)x+.14D, (double)y+.14D, (double)z,      (double)x+.86D, (double)y+.86D, (double)z+.19D);
                case SOUTH: return AxisAlignedBB.fromBounds((double)x+.14D, (double)y+.14D, (double)z+.81D, (double)x+.86D, (double)y+.86D, (double)z+1);
                case WEST:  return AxisAlignedBB.fromBounds((double)x,      (double)y+.14D, (double)z+.14D, (double)x+.19D, (double)y+.86D, (double)z+.86D);
                case EAST:  return AxisAlignedBB.fromBounds((double)x+1,    (double)y+.14D, (double)z+.86D, (double)x+.81D, (double)y+.86D, (double)z+.14D);
                default:    return AxisAlignedBB.fromBounds((double)x,      (double)y,      (double)z,      (double)x+1,    (double)y+1,    (double)z+1);
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        switch (world.getBlockState(pos).getValue(facing))
        {
            case DOWN:  return AxisAlignedBB.fromBounds((double)x+.13D, (double)y,      (double)z+.13D, (double)x+.87D, (double)y+.19D, (double)z+.87D);
            case UP:    return AxisAlignedBB.fromBounds((double)x+.13D, (double)y+.81D, (double)z+.13D, (double)x+.87D, (double)y+1D,   (double)z+.87D);
            case NORTH: return AxisAlignedBB.fromBounds((double)x+.13D, (double)y+.13D, (double)z,      (double)x+.87D, (double)y+.87D, (double)z+.2D);
            case SOUTH: return AxisAlignedBB.fromBounds((double)x+.13D, (double)y+.13D, (double)z+.81D, (double)x+.87D, (double)y+.87D, (double)z+1);
            case WEST:  return AxisAlignedBB.fromBounds((double)x,      (double)y+.13D, (double)z+.13D, (double)x+.2D,  (double)y+.87D, (double)z+.87D);
            case EAST:  return AxisAlignedBB.fromBounds((double)x+1,    (double)y+.13D, (double)z+.87D, (double)x+.8D,  (double)y+.87D, (double)z+.13D);
            default:    return AxisAlignedBB.fromBounds((double)x,      (double)y,      (double)z,      (double)x+1,    (double)y+1,    (double)z+1);
        }
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 startVec, Vec3 endVec)
    {
        return super.collisionRayTrace(world, pos, startVec, endVec);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityInvisibleTesseract();
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

    @Override
    public boolean isFullCube()
    {
        return false;
    }
}