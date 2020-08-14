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

import XFactHD.rfutilities.common.blocks.block.BlockCapacitor;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityCapacitor extends TileEntityBaseRFU implements ITickable, IEnergyProvider, IEnergyReceiver
{
    public int type = 0;
    public EnergyStorage storage = new EnergyStorage( storageByType(), receiveByType(), extractByType());

    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockCapacitor.ORIENTATION).getIndex())
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
        switch (worldObj.getBlockState(pos).getValue(BlockCapacitor.ORIENTATION).getIndex())
        {
            case 2: if(facing == EnumFacing.SOUTH) return storage.receiveEnergy(amount, simulate);
            case 3: if(facing == EnumFacing.WEST)  return storage.receiveEnergy(amount, simulate);
            case 4: if(facing == EnumFacing.NORTH) return storage.receiveEnergy(amount, simulate);
            case 5: if(facing == EnumFacing.EAST)  return storage.receiveEnergy(amount, simulate);
            default: return 0;
        }
    }

    @Override
    public int extractEnergy(EnumFacing facing, int amount, boolean simulate)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockCapacitor.ORIENTATION).getIndex())
        {
            case 2: if(facing == EnumFacing.NORTH) return storage.extractEnergy(amount, simulate);
            case 3: if(facing == EnumFacing.EAST)  return storage.extractEnergy(amount, simulate);
            case 4: if(facing == EnumFacing.SOUTH) return storage.extractEnergy(amount, simulate);
            case 5: if(facing == EnumFacing.WEST)  return storage.extractEnergy(amount, simulate);
            default: return 0;
        }
    }

    @Override
    public int getEnergyStored(EnumFacing facing)
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing facing)
    {
        return storage.getMaxEnergyStored();
    }

    @Override
    public void update()
    {
		if (storage.getMaxEnergyStored() != storageByType())
        {
            storage.setCapacity(storageByType());
            storage.setMaxReceive(receiveByType());
            storage.setMaxExtract(extractByType());
        }
        transferEnergy(worldObj.getBlockState(pos));
    }

    private int transferEnergy(IBlockState state)
    {
        if (!worldObj.isRemote)
        {

            switch (state.getValue(BlockCapacitor.ORIENTATION).getIndex())
            {
                case 0: return transferPower(EnumFacing.NORTH);
                case 1: return transferPower(EnumFacing.EAST);
                case 2: return transferPower(EnumFacing.SOUTH);
                case 3: return transferPower(EnumFacing.WEST);
                default: return 0;
            }
        }
        return 0;
    }

    private int transferPower(EnumFacing side)
    {
        TileEntity te = worldObj.getTileEntity(pos.offset(side));
        if (te instanceof IEnergyReceiver && ((IEnergyReceiver)te).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), true) > 0)
        {
            return ((IEnergyReceiver)te).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), type==0), false);
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
        type = nbt.getInteger("TYPE");
		storage.setCapacity(storageByType());
		storage.setMaxReceive(receiveByType());
		storage.setMaxExtract(extractByType());
        storage.readFromNBT(nbt);
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setInteger("TYPE", type);
        storage.writeToNBT(nbt);
    }
}
