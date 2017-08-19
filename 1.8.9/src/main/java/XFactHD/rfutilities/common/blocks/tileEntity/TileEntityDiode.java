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

import XFactHD.rfutilities.common.blocks.block.BlockDiode;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityDiode extends TileEntityBaseRFU implements IEnergyProvider, IEnergyReceiver
{
    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockDiode.facing).getIndex())
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
        if (!worldObj.isRemote)
        {
            switch (worldObj.getBlockState(pos).getValue(BlockDiode.facing).getIndex())
            {
                case 2: return facing == EnumFacing.SOUTH ? receivePower(facing, amount, simulate) : 0;
                case 3: return facing == EnumFacing.WEST  ? receivePower(facing, amount, simulate) : 0;
                case 4: return facing == EnumFacing.NORTH ? receivePower(facing, amount, simulate) : 0;
                case 5: return facing == EnumFacing.EAST  ? receivePower(facing, amount, simulate) : 0;
                default: return 0;
            }
        }
        return 0;
    }

    private int receivePower(EnumFacing facing, int amount, boolean simulate)
    {
        EnumFacing opposite = facing.getOpposite();
        TileEntity te = worldObj.getTileEntity(pos.offset(opposite));
        if (te instanceof IEnergyReceiver && ((IEnergyReceiver)te).receiveEnergy(facing, amount, true) > 0)
        {
            return ((IEnergyReceiver)te).receiveEnergy(facing, amount, simulate);
        }
        return 0;
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
