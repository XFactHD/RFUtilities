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

package XFactHD.rfutilities.common.blocks.tileEntity;

import XFactHD.rfutilities.common.blocks.block.BlockInvisibleTesseract;
import XFactHD.rfutilities.common.blocks.interfaces.IEnergyTesseract;
import XFactHD.rfutilities.common.utils.LogHelper;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Set;

//TODO: test, make FE and TESLA compatible, fix hitbox and make models show status and sender/receiver
public class TileEntityInvisibleTesseract extends TileEntityBaseRFU implements IEnergyTesseract, IEnergyProvider, IEnergyReceiver
{
    public boolean hidden = false;
    public boolean isActive = false;
    public boolean isSender = true;

    private HashMap<BlockPos, TileEntityInvisibleTesseract> tesseracts = new HashMap<>();

    public TileEntityInvisibleTesseract dialedSender = null;
    private BlockPos posDialedSender;

    @Override
    public void onLoad()
    {
        super.onLoad();
        initialize();
    }

    private void initialize()
    {
        if (isSender && isActive)
        {
            for (BlockPos recPos : tesseracts.keySet())
            {
                try
                {
                    TileEntity te = worldObj.getTileEntity(recPos);
                    if (te instanceof TileEntityInvisibleTesseract)
                    {
                        TileEntityInvisibleTesseract tesseract = ((TileEntityInvisibleTesseract)te);
                        tesseracts.put(tesseract.getPos(), tesseract);
                    }
                    else
                    {
                        LogHelper.warn("TileEntityInvisibleTesseract at " + recPos.toString() + " somehow got changed!");
                        tesseracts.remove(recPos);
                    }
                }
                catch (Exception e)
                {
                    LogHelper.warn("A TileEntity tileInvisTess(XFactHD.rfutilities.common.blocks.tileEntity.TileEntityInvisibleTesseract) has thrown an exception during initialization.");
                    e.printStackTrace();
                    tesseracts.remove(recPos);
                }
            }
        }
        else if (!isSender && isActive)
        {
            try
            {
                if (worldObj.getTileEntity(posDialedSender) instanceof TileEntityInvisibleTesseract)
                {
                    dialedSender = ((TileEntityInvisibleTesseract)worldObj.getTileEntity(posDialedSender));
                }
                else
                {
                    LogHelper.warn("TileEntityInvisibleTesseract at " + posDialedSender.toString() + " somehow got changed!");
                    clearFrequency();
                    posDialedSender = null;
                    isActive = false;
                }
            }
            catch (Exception e)
            {
                LogHelper.warn("A TileEntity tileInvisTess(XFactHD.rfutilities.common.blocks.tileEntity.TileEntityInvisibleTesseract) has thrown an exception during loading.");
                e.printStackTrace();
                posDialedSender = null;
                isActive = false;
            }
        }
    }

    //IEnergyHandler
    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        return facing == worldObj.getBlockState(pos).getValue(BlockInvisibleTesseract.ORIENTATION);
    }

    @Override
    public int receiveEnergy(EnumFacing facing, int amount, boolean simulate)
    {
        //TODO: add compatibility for FE and TESLA
        if (isActive && canConnectEnergy(facing) && isSender && !tesseracts.isEmpty())
        {
            int toSend = 0;
            for (BlockPos pos : tesseracts.keySet())
            {
                toSend += tesseracts.get(pos).receiveEnergy(amount, simulate);
            }
            return toSend;
        }
        return 0;
    }

    @Override
    public int extractEnergy(EnumFacing facing, int amount, boolean b)
    {
        return 0;
    }

    @Override
    public int getEnergyStored(EnumFacing facing)
    {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing facing)
    {
        return 0;
    }

    //Internal
    public void setActive(boolean active)
    {
        this.isActive = active;
        worldObj.scheduleUpdate(pos, worldObj.getBlockState(pos).getBlock(), 0);
    }

    public void setSender(boolean sender)
    {
        this.isSender = sender;
        worldObj.scheduleUpdate(pos, worldObj.getBlockState(pos).getBlock(), 0);
    }

    public void addReceiver(TileEntityInvisibleTesseract receiver)
    {
        tesseracts.put(receiver.getPos(), receiver);
    }

    public void setDialedSender(TileEntityInvisibleTesseract dialedSender)
    {
        this.dialedSender = dialedSender;
        posDialedSender = dialedSender != null ?  dialedSender.getPos() : null;
    }

    public Set<BlockPos> getDialedReceivers()
    {
        return tesseracts.keySet();
    }

    //IEnergyTesseract
    @Override
    public boolean canSendEnergy()
    {
        return isActive && isSender;
    }

    @Override
    public boolean canReceiveEnergy()
    {
        return isActive && !isSender;
    }

    @Override
    public void clearFrequency()
    {
        setActive(false);
        if (isSender)
        {
            tesseracts.clear();
        }
        setSender(true);
        setDialedSender(null);
    }

    @Override
    public int receiveEnergy(int amount, boolean simulate)
    {
        //TODO: add compatibility for FE and TESLA
        EnumFacing facing = worldObj.getBlockState(pos).getValue(BlockInvisibleTesseract.ORIENTATION);
        TileEntity te = worldObj.getTileEntity(pos.offset(facing));
        if (te instanceof IEnergyReceiver && (((IEnergyReceiver)te).receiveEnergy(facing.getOpposite(), amount, true)) > 0)
        {
            return (((IEnergyReceiver)te).receiveEnergy(facing.getOpposite(), amount, simulate));
        }
        return 0;
    }

    //NBT Stuff
    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        hidden = nbt.getBoolean("hidden");
        isActive = nbt.getBoolean("isActive");
        isSender = nbt.getBoolean("isSender");
        if (isSender && isActive)
        {
            tesseracts = new HashMap<BlockPos, TileEntityInvisibleTesseract>();
            NBTTagList list = nbt.getTagList("tesseracts", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.tagCount(); i++)
            {
                NBTTagCompound tess = list.getCompoundTagAt(i);
                tesseracts.put(BlockPos.fromLong(tess.getLong("pos")), null);
            }
        }
        else if (!isSender && isActive)
        {
            posDialedSender = BlockPos.fromLong(nbt.getLong("posSender"));
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setBoolean("hidden", hidden);
        nbt.setBoolean("isActive", isActive);
        nbt.setBoolean("isSender", isSender);
        if (isSender && isActive)
        {
            NBTTagList tesseracts = new NBTTagList();
            for (BlockPos pos : this.tesseracts.keySet())
            {
                NBTTagCompound tess = new NBTTagCompound();
                tess.setLong("pos", pos.toLong());
                tesseracts.appendTag(tess);
            }
            nbt.setTag("tesseracts", tesseracts);
        }
        else if (!isSender && isActive)
        {
            nbt.setLong("posSender", posDialedSender.toLong());
        }
    }
}
