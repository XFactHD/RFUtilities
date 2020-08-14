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

import XFactHD.rfutilities.common.blocks.block.BlockRFMeter;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityRFMeter extends TileEntityBaseRFU implements ITickable, IEnergyProvider, IEnergyReceiver
{
    private int lastRF = 0;
    public int lastRFDisp = 0;
    public String lastRFMark = "RF/t";
    private int transferedRF = 0;
    public int transferedRFDisp = 0;
    public String transferedRFMark = "RF";

    @Override
    public void update()
    {
        lastRFDisp = transformLastRFScientific(lastRF);
        transferedRFDisp = transformTransferedScientific(transferedRF);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockRFMeter.facing).getIndex())
        {
            case 2: return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
            case 3: return (facing == EnumFacing.EAST  || facing == EnumFacing.WEST);
            case 4: return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
            case 5: return (facing == EnumFacing.EAST  || facing == EnumFacing.WEST);
            default: return false;
        }
    }

    @Override
    public int receiveEnergy(EnumFacing facing, int amount, boolean simulate)
    {
        EnumFacing opposite = facing.getOpposite();
        TileEntity te = worldObj.getTileEntity(pos.offset(opposite));
        if (canConnectEnergy(facing) && te instanceof IEnergyReceiver && (((IEnergyReceiver)te).receiveEnergy(facing, amount, true) > 0))
        {
            transferedRF = transferedRF + ((IEnergyReceiver)te).receiveEnergy(facing, amount, true);
            return lastRF = ((IEnergyReceiver)te).receiveEnergy(facing, amount, simulate);
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
    public int extractEnergy(EnumFacing facing, int amount, boolean simulate)
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

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {

    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {

    }
}
