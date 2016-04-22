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

import XFactHD.rfutilities.common.blocks.interfaces.IEnergyTesseract;
import XFactHD.rfutilities.common.utils.LogHelper;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityInvisibleTesseract extends TileEntityBaseRFU implements IEnergyTesseract, IEnergyHandler
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
    public void updateEntity()
    {
        super.updateEntity();
        //LogHelper.info("Updating!");
        if (!worldObj.isRemote)
        {
            if (ticksUntilInit > 0)
            {
                //LogHelper.info("1");
                --ticksUntilInit;
            }
            else if (ticksUntilInit == 0 && !initialized)
            {
                //LogHelper.info("0");
                initialize();
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
                    TileEntity te = worldObj.getTileEntity(xPosDialedReceivers[i], yPosDialedReceivers[i], zPosDialedReceivers[i]);
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
                if (worldObj.getTileEntity(xPosDialedSender, yPosDialedSender, zPosDialedSender) instanceof TileEntityInvisibleTesseract)
                {
                    dialedSender = ((TileEntityInvisibleTesseract)worldObj.getTileEntity(xPosDialedSender, yPosDialedSender, zPosDialedSender));
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
    public boolean canConnectEnergy(ForgeDirection fd)
    {
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
        {
            case 0: return fd == ForgeDirection.DOWN;
            case 1: return fd == ForgeDirection.UP;
            case 2: return fd == ForgeDirection.WEST;
            case 3: return fd == ForgeDirection.NORTH;
            case 4: return fd == ForgeDirection.EAST;
            case 5: return fd == ForgeDirection.SOUTH;
            default: return false;
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection fd, int amount, boolean simulate)
    {
        if (isActive && canConnectEnergy(fd))
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
    public int extractEnergy(ForgeDirection forgeDirection, int amount, boolean b)
    {
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection)
    {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection)
    {
        return 0;
    }

    //Internal
    public void setActive(boolean active)
    {
        this.isActive = active;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public void setSender(boolean sender)
    {
        this.isSender = sender;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
        ForgeDirection fd = ForgeDirection.UNKNOWN;
        for (int i = 0; i < 6; i++)
        {
            if (canConnectEnergy(ForgeDirection.getOrientation(i)))
            {
                fd = ForgeDirection.getOrientation(i);
                break;
            }
        }
        TileEntity te = worldObj.getTileEntity(xCoord + fd.offsetX, yCoord + fd.offsetY, zCoord + fd.offsetZ);
        if (te instanceof IEnergyReceiver && (((IEnergyReceiver)te).receiveEnergy(fd.getOpposite(), amount, true)) > 0)
        {
            return (((IEnergyReceiver)te).receiveEnergy(fd.getOpposite(), amount, simulate));
        }
        return 0;
    }

    //NBT Stuff
    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        //LogHelper.info("Reading from NBT!");
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
        //LogHelper.info("Writing to NBT!");
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
                xPos[i] = te.xCoord;
                yPos[i] = te.yCoord;
                zPos[i] = te.zCoord;
            }
            nbt.setIntArray("xPosArray", xPos);
            nbt.setIntArray("yPosArray", yPos);
            nbt.setIntArray("zPosArray", zPos);
        }
        else if (!isSender && isActive)
        {
            nbt.setInteger("senderX", dialedSender.xCoord);
            nbt.setInteger("senderY", dialedSender.yCoord);
            nbt.setInteger("senderZ", dialedSender.zCoord);
        }
    }
}
