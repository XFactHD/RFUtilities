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

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityInvisibleTesseract;
import XFactHD.rfutilities.common.items.ItemDialer;
import XFactHD.rfutilities.common.items.ItemMultimeter;
import XFactHD.rfutilities.common.utils.EventHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockInvisibleTesseract extends BlockBaseRFU
{
    public static PropertyDirection ORIENTATION = PropertyDirection.create("facing");

    public BlockInvisibleTesseract()
    {
        super("blockInvisibleTesseract", Material.IRON, "");
        this.addItemBlock(new ItemBlock(this));
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing o = EnumFacing.NORTH;

        int l = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int m = MathHelper.floor_double((double) (placer.rotationPitch * 4.0F / 360.0F) + 0.5D) & 3;

        if (m == 1)
        {
            o = EnumFacing.DOWN;
        }
        else if (m == 3)
        {
            o = EnumFacing.UP;
        }
        else if (m == 0)
        {
            if (l == 0)
            {
                o = EnumFacing.SOUTH;
            }

            if (l == 1)
            {
                o = EnumFacing.WEST;
            }

            if (l == 2)
            {
                o = EnumFacing.NORTH;
            }

            if (l == 3)
            {
                o = EnumFacing.EAST;
            }
        }

        return getDefaultState().withProperty(ORIENTATION, o);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ORIENTATION);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(ORIENTATION, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ORIENTATION).getIndex();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, 0);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntityInvisibleTesseract tesseract = ((TileEntityInvisibleTesseract)world.getTileEntity(pos));
            if (tesseract == null) { return false; }

            if(heldItem != null && heldItem.getItem() instanceof ItemDialer)
            {
                handleDialerInteraction(world, player, heldItem, tesseract);
            }
            else if (heldItem != null && heldItem.getItem() instanceof ItemMultimeter)
            {
                handleMultiMeterInteraction(player, tesseract);
            }
        }
        return true;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        TileEntity te = world.getTileEntity(pos);
        if (EventHandler.canItemShowTess || (te instanceof TileEntityInvisibleTesseract  && !((TileEntityInvisibleTesseract)te).hidden))
        {
            switch (world.getBlockState(pos).getValue(ORIENTATION))
            {
                case DOWN:  return new AxisAlignedBB(.13F,   0F, .13F, .87F, .19F, .87F);
                case UP:    return new AxisAlignedBB(.13F, .81F, .13F, .87F,   1F, .87F);
                case NORTH: return new AxisAlignedBB(.14F, .14F,   0F, .86F, .86F, .19F);
                case SOUTH: return new AxisAlignedBB(.14F, .14F, .81F, .86F, .86F,   1F);
                case WEST:  return new AxisAlignedBB(  0F, .14F, .14F, .19F, .86F, .86F);
                case EAST:  return new AxisAlignedBB(  1F, .14F, .86F, .81F, .86F, .14F);
                default:    return new AxisAlignedBB(  0F,   0F,   0F,   1F,   1F,   1F);
            }
        }
        else
        {
            return new AxisAlignedBB(0F, 0F, 0F, 0F, 0F, 0F);
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        //noinspection ConstantConditions
        return getCollisionBoundingBox(state, world, pos).offset(pos);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityInvisibleTesseract();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return side == world.getBlockState(pos).getValue(ORIENTATION);
    }

    private void handleDialerInteraction(World world, EntityPlayer player, ItemStack stack, TileEntityInvisibleTesseract tesseract)
    {
        if (stack.hasTagCompound())
        {
            //noinspection ConstantConditions
            BlockPos tePos = BlockPos.fromLong(stack.getTagCompound().getLong("senderPos"));

            if (!stack.getTagCompound().getBoolean("modeDial"))
            {
                tesseract.clearFrequency();
                player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:clearedTess.name"));
            }
            else
            {
                TileEntity teTess = world.getTileEntity(tePos);
                if (teTess instanceof TileEntityInvisibleTesseract)
                {
                    TileEntityInvisibleTesseract sender = ((TileEntityInvisibleTesseract)teTess);
                    if (sender != tesseract)
                    {
                        if (tesseract.dialedSender != null && tesseract.dialedSender == sender)
                        {
                            player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:alreadyDialed.name"));
                        }
                        else
                        {
                            tesseract.setActive(true);
                            tesseract.setSender(false);
                            tesseract.setDialedSender(sender);
                            sender.addReceiver(tesseract);
                            player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:dialedToSender.name"));
                        }
                    }
                    else
                    {
                        player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:cantDialSame.name", TextFormatting.RED));
                    }
                }
                else
                {
                    player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:missingSender.name"));
                    //noinspection ConstantConditions
                    stack.setTagCompound(null);
                }
            }

        }
        else
        {
            NBTTagCompound compound = new NBTTagCompound();
            long tePos = tesseract.getPos().toLong();
            compound.setLong("senderPos", tePos);
            stack.setTagCompound(compound);
            if (!tesseract.isActive)
            {
                tesseract.setActive(true);
            }
            tesseract.setSender(true);
            player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:dialedToDialer.name"));
        }
    }

    private void handleMultiMeterInteraction(EntityPlayer player, TileEntityInvisibleTesseract tesseract)
    {
        if (!player.isSneaking())
        {
            if (tesseract.isActive && tesseract.isSender)
            {
                player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:isSender.name"));
                player.addChatComponentMessage(new TextComponentString(I18n.format("desc.rfutilities:dialedTo.name")));
                for (BlockPos recPos : tesseract.getDialedReceivers())
                {
                    player.addChatComponentMessage(new TextComponentString(I18n.format("desc.rfutilities:tessAt.name") + " " + recPos.toString()));
                }
            }
            else if (tesseract.isActive)
            {
                BlockPos tePos = tesseract.dialedSender.getPos();
                player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:isReceiver.name"));
                player.addChatComponentMessage(new TextComponentString(I18n.format("desc.rfutilities:dialedTo.name") + " " + I18n.format("desc.rfutilities:tessAt.name") + " " + tePos.toString() + "."));
            }
            else
            {
                player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:undialed.name"));
            }
        }
        else
        {
            tesseract.hidden = !tesseract.hidden;
            player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:hidden_" + Boolean.toString(tesseract.hidden) + ".name"));
        }
    }
}