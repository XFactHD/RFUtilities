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

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDiode extends TileEntityBaseRFU implements IEnergyHandler
{
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
        if (!worldObj.isRemote)
        {
            switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
            {
                case 2: return fd == ForgeDirection.SOUTH ? receivePower(fd, amount, simulate) : 0;
                case 3: return fd == ForgeDirection.WEST  ? receivePower(fd, amount, simulate) : 0;
                case 4: return fd == ForgeDirection.NORTH ? receivePower(fd, amount, simulate) : 0;
                case 5: return fd == ForgeDirection.EAST  ? receivePower(fd, amount, simulate) : 0;
                default: return 0;
            }
        }
        return 0;
    }

    private int receivePower(ForgeDirection fd, int amount, boolean simulate)
    {
        ForgeDirection opposite = fd.getOpposite();
        TileEntity te = worldObj.getTileEntity(xCoord+opposite.offsetX, yCoord, zCoord+opposite.offsetZ);
        if (te instanceof IEnergyReceiver && ((IEnergyReceiver)te).receiveEnergy(fd, amount, true) > 0)
        {
            return ((IEnergyReceiver)te).receiveEnergy(fd, amount, simulate);
        }
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection fd, int amount, boolean simulate)
    {
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection fd)
    {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection fd)
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
