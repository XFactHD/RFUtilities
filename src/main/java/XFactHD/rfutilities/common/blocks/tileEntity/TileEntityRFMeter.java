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

import XFactHD.rfutilities.RFUtilities;
import cofh.api.energy.IEnergyHandler;
import cofh.thermalexpansion.block.cell.TileCellCreative;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRFMeter extends TileEntityBaseRFU implements IEnergyHandler
{
    private int lastRF = 0;
    public int lastRFDisp = 0;
    public String lastRFMark = "RF/t";
    private int transferedRF = 0;
    public int transferedRFDisp = 0;
    public String transferedRFMark = "RF";

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        lastRFDisp = transformLastRFScientific(lastRF);
        transferedRFDisp = transformTransferedScientific(transferedRF);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection fd)
    {
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
        {
            case 2: return (fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH);
            case 3: return (fd == ForgeDirection.EAST || fd == ForgeDirection.WEST);
            case 4: return (fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH);
            case 5: return (fd == ForgeDirection.EAST || fd == ForgeDirection.WEST);
            default: return false;
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection fd, int amount, boolean simulate)
    {
        ForgeDirection opposite = fd.getOpposite();
        TileEntity te = worldObj.getTileEntity(xCoord + opposite.offsetX, yCoord, zCoord + opposite.offsetZ);
        if (canConnectEnergy(fd) && te instanceof IEnergyHandler && (((IEnergyHandler)te).receiveEnergy(fd, amount, true) > 0))
        {
            transferedRF = transferedRF + ((IEnergyHandler)te).receiveEnergy(fd, amount, true);
            return lastRF = ((IEnergyHandler)te).receiveEnergy(fd, amount, simulate);
        }
        else
        {
            return 0;
        }
    }

    private int transformLastRFScientific(int value)
    {
        if (value > 1000000000)
        {
            lastRFMark = "GRF/t";
            return value/1000000000;
        }
        else if (value > 1000000)
        {
            lastRFMark = "MRF/t";
            return value/1000000;
        }
        else if (value > 1000)
        {
            lastRFMark = "kRF/t";
            return value/1000;
        }
        else
        {
            lastRFMark = "RF/t";
            return value;
        }
    }

    private int transformTransferedScientific(int value)
    {
        if (value > 1000000000)
        {
            transferedRFMark = "GRF";
            return value/1000000000;
        }
        else if (value > 1000000)
        {
            transferedRFMark = "MRF";
            return value/1000000;
        }
        else if (value > 1000)
        {
            transferedRFMark = "kRF";
            return value/1000;
        }
        else
        {
            transferedRFMark = "RF";
            return value;
        }
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int amount, boolean simulate)
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

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {

    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {

    }
}
