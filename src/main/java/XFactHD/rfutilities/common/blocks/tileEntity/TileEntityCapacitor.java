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

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCapacitor extends TileEntityBaseRFU implements IEnergyHandler
{
    public int type = 0;
    public EnergyStorage storage = new EnergyStorage( storageByType(), receiveByType(), extractByType());

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
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
        {
            case 2: if(fd == ForgeDirection.SOUTH) return storage.receiveEnergy(amount, simulate);
            case 3: if(fd == ForgeDirection.WEST)  return storage.receiveEnergy(amount, simulate);
            case 4: if(fd == ForgeDirection.NORTH) return storage.receiveEnergy(amount, simulate);
            case 5: if(fd == ForgeDirection.EAST)  return storage.receiveEnergy(amount, simulate);
            default: return 0;
        }
    }

    @Override
    public int extractEnergy(ForgeDirection fd, int amount, boolean simulate)
    {
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
        {
            case 2: if(fd == ForgeDirection.NORTH) return storage.extractEnergy(amount, simulate);
            case 3: if(fd == ForgeDirection.EAST)  return storage.extractEnergy(amount, simulate);
            case 4: if(fd == ForgeDirection.SOUTH) return storage.extractEnergy(amount, simulate);
            case 5: if(fd == ForgeDirection.WEST)  return storage.extractEnergy(amount, simulate);
            default: return 0;
        }
    }

    @Override
    public int getEnergyStored(ForgeDirection fd)
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection fd)
    {
        return storage.getMaxEnergyStored();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
		if (storage.getMaxEnergyStored() != storageByType())
        {
            storage.setCapacity(storageByType());
            storage.setMaxReceive(receiveByType());
            storage.setMaxExtract(extractByType());
        }
        transferEnergy(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
    }

    private int transferEnergy(int meta)
    {
        if (!worldObj.isRemote)
        {
            switch (meta)
            {
                case 2: return transferPower(ForgeDirection.NORTH);
                case 3: return transferPower(ForgeDirection.EAST);
                case 4: return transferPower(ForgeDirection.SOUTH);
                case 5: return transferPower(ForgeDirection.WEST);
                default: return 0;
            }
        }
        return 0;
    }

    private int transferPower(ForgeDirection fd)
    {
        TileEntity te = worldObj.getTileEntity(xCoord+fd.offsetX, yCoord, zCoord+fd.offsetZ);
        if (te instanceof IEnergyReceiver && ((IEnergyReceiver)te).receiveEnergy(fd.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), true) > 0)
        {
            return ((IEnergyReceiver)te).receiveEnergy(fd.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), type==0), false);
        }
        return 0;
    }

    private int storageByType()
    {
        switch (type)
        {
            //capacity, receive, extract
            case 1: return 80000;
            case 2: return 400000;
            case 3: return 4000000;
            case 4: return 20000000;
            case 5: return 100000;
            case 6: return 200000;
            case 7: return 500000;
            default: return 0;
        }
    }

    private int receiveByType()
    {
        switch (type)
        {
            case 1: return 200;
            case 2: return 800;
            case 3: return 8000;
            case 4: return 32000;
            case 5: return 200;
            case 6: return 600;
            case 7: return 1000;
            default: return 0;
        }
    }

    private int extractByType()
    {
        switch (type)
        {
            case 1: return 80;
            case 2: return 400;
            case 3: return 4000;
            case 4: return 16000;
            case 5: return 200;
            case 6: return 400;
            case 7: return 800;
            default: return 0;
        }
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        type = nbt.getInteger("type");
		storage.setCapacity(storageByType());
		storage.setMaxReceive(receiveByType());
		storage.setMaxExtract(extractByType());
        storage.readFromNBT(nbt);
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setInteger("type", type);
        storage.writeToNBT(nbt);
    }
}
