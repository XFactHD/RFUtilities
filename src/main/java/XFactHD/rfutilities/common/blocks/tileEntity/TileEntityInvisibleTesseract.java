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

package XFactHD.rfutilities.common.blocks.tileEntity;

import XFactHD.rfutilities.common.blocks.block.BlockInvisibleTesseract;
import XFactHD.rfutilities.common.blocks.interfaces.IEnergyTesseract;
import XFactHD.rfutilities.common.utils.LogHelper;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import java.util.ArrayList;

public class TileEntityInvisibleTesseract extends TileEntityBaseRFU implements ITickable, IEnergyTesseract, IEnergyProvider, IEnergyReceiver
{
    private boolean initialized = false;
    private int ticksUntilInit = 1;
    public boolean hidden = false;
    public boolean isActive = false;
    public boolean isSender = true;
    public TileEntityInvisibleTesseract dialedSender = null;
    private ArrayList<TileEntityInvisibleTesseract> tesseracts = new ArrayList<TileEntityInvisibleTesseract>();
    private int[] xPosDialedReceivers;
    private int[] yPosDialedReceivers;
    private int[] zPosDialedReceivers;
    private int xPosDialedSender;
    private int yPosDialedSender;
    private int zPosDialedSender;

    public TileEntityInvisibleTesseract()
    {

    }

    public void clicked()
    {
        LogHelper.info(dialedSender == null ? "null" : dialedSender.toString());
        LogHelper.info(isActive);
    }

    @Override
    public void update()
    {
        if (!worldObj.isRemote)
        {
            if (ticksUntilInit > 0)
            {
                --ticksUntilInit;
            }
            else if (ticksUntilInit == 0 && !initialized)
            {
                initialize();
                worldObj.markBlockForUpdate(pos);
            }
        }
    }

    private void initialize()
    {
        if (isSender && isActive)
        {
            for (int i = 0; i < xPosDialedReceivers.length; i++)
            {
                try
                {
                    BlockPos recPos = new BlockPos(xPosDialedReceivers[i], yPosDialedReceivers[i], zPosDialedReceivers[i]);
                    TileEntity te = worldObj.getTileEntity(recPos);
                    if (te instanceof TileEntityInvisibleTesseract)
                    {
                        TileEntityInvisibleTesseract tesseract = ((TileEntityInvisibleTesseract)te);
                        tesseracts.add(tesseract);
                    }
                    else
                    {
                        LogHelper.warn("TileEntityInvisibleTesseract at " + xPosDialedReceivers[i] + " " + yPosDialedReceivers[i] + " " + zPosDialedReceivers[i] + " somehow got changed!");
                    }
                }
                catch (Exception e)
                {
                    LogHelper.warn("A TileEntity tileInvisTess(XFactHD.rfutilities.common.blocks.tileEntity.TileEntityInvisibleTesseract) has thrown an exception during loading.");
                    e.printStackTrace();
                }
            }
        }
        else if (!isSender && isActive)
        {
            try
            {
                BlockPos sendPos = new BlockPos(xPosDialedSender, yPosDialedSender, zPosDialedSender);
                if (worldObj.getTileEntity(sendPos) instanceof TileEntityInvisibleTesseract)
                {
                    dialedSender = ((TileEntityInvisibleTesseract)worldObj.getTileEntity(sendPos));
                }
                else
                {
                    LogHelper.warn("TileEntityInvisibleTesseract at " + xPosDialedSender + " " + yPosDialedSender + " " + zPosDialedSender + " somehow got changed!");
                    clearFrequency();
                }
            }
            catch (Exception e)
            {
                LogHelper.warn("A TileEntity tileInvisTess(XFactHD.rfutilities.common.blocks.tileEntity.TileEntityInvisibleTesseract) has thrown an exception during loading.");
                e.printStackTrace();
            }
        }
        else
        {
            LogHelper.info("Sender: " + isSender + ", Active: " + isActive);
        }
        initialized = true;
    }

    //IEnergyHandler
    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        return facing == worldObj.getBlockState(pos).getValue(BlockInvisibleTesseract.facing);
    }

    @Override
    public int receiveEnergy(EnumFacing facing, int amount, boolean simulate)
    {
        if (isActive && canConnectEnergy(facing))
        {
            if (isSender)
            {
                if (!tesseracts.isEmpty())
                {
                    int toSend = 0;
                    for (TileEntityInvisibleTesseract te:tesseracts)
                    {
                        toSend += te.receiveEnergy(amount, simulate);
                    }
                    return toSend;
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
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
        worldObj.markBlockForUpdate(pos);
    }

    public void setSender(boolean sender)
    {
        this.isSender = sender;
        worldObj.markBlockForUpdate(pos);
    }

    public void addReceiver(TileEntityInvisibleTesseract receiver)
    {
        tesseracts.add(receiver);
    }

    public void setDialedSender(TileEntityInvisibleTesseract dialedSender)
    {
        this.dialedSender = dialedSender;
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
        EnumFacing facing = EnumFacing.UP;
        for (int i = 0; i < 6; i++)
        {
            if (canConnectEnergy(EnumFacing.getFront(i)))
            {
                facing = EnumFacing.getFront(i);
                break;
            }
        }
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
            xPosDialedReceivers = nbt.getIntArray("xPosArray");
            yPosDialedReceivers = nbt.getIntArray("yPosArray");
            zPosDialedReceivers = nbt.getIntArray("zPosArray");
        }
        else if (!isSender && isActive)
        {
            xPosDialedSender = nbt.getInteger("senderX");
            yPosDialedSender = nbt.getInteger("senderY");
            zPosDialedSender = nbt.getInteger("senderZ");
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
            int[] xPos = new int[tesseracts.size()];
            int[] yPos = new int[tesseracts.size()];
            int[] zPos = new int[tesseracts.size()];
            for (int i = 0; i < tesseracts.size(); i++)
            {
                TileEntityInvisibleTesseract te = tesseracts.get(i);
                xPos[i] = te.pos.getX();
                yPos[i] = te.pos.getY();
                zPos[i] = te.pos.getZ();
            }
            nbt.setIntArray("xPosArray", xPos);
            nbt.setIntArray("yPosArray", yPos);
            nbt.setIntArray("zPosArray", zPos);
        }
        else if (!isSender && isActive)
        {
            nbt.setInteger("senderX", dialedSender.pos.getX());
            nbt.setInteger("senderY", dialedSender.pos.getY());
            nbt.setInteger("senderZ", dialedSender.pos.getZ());
        }
    }
}
