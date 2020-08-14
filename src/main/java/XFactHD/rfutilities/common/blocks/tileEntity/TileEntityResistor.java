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
import XFactHD.rfutilities.common.blocks.block.BlockResistor;
import XFactHD.rfutilities.common.net.PacketGetThroughput;
import XFactHD.rfutilities.common.net.PacketSetThroughput;
import XFactHD.rfutilities.common.net.PacketSetTransferMode;
import XFactHD.rfutilities.common.net.PacketWantThroughput;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityResistor extends TileEntityBaseRFU implements ITickable, IEnergyProvider, IEnergyReceiver
{
    public int throughput = 0;
    private boolean transferOncePerTick = false;
    //private boolean wait = false;

    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockResistor.facing).getIndex())
        {
            case 2: return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
            case 3: return (facing == EnumFacing.EAST  || facing == EnumFacing.WEST);
            case 4: return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
            case 5: return (facing == EnumFacing.EAST  || facing == EnumFacing.WEST);
            default: return false;
        }
    }

    @Override
    public void update()
    {
        //wait = false;
    }

    @Override
    public int receiveEnergy(EnumFacing facing, int amount, boolean simulate)
    {
        //if (transferOncePerTick && !wait)
        //{
        //    wait = true;
        //}

        EnumFacing opposite = facing.getOpposite();
        TileEntity te = worldObj.getTileEntity(pos.offset(opposite));
        if (canConnectEnergy(facing) && te instanceof IEnergyReceiver && (((IEnergyReceiver)te).receiveEnergy(facing, amount, true) > 0))
        {
            if (amount<=throughput)
            {
                return ((IEnergyReceiver)te).receiveEnergy(facing, amount, simulate);
            }
            else
            {
                return ((IEnergyReceiver)te).receiveEnergy(facing, throughput, simulate);
            }
        }
        else
        {
            return 0;
        }
    }

    public void setThroughput(int value)
    {
        if (worldObj.isRemote)
        {
            RFUtilities.RFU_NET_WRAPPER.sendToServer(new PacketSetThroughput(pos.getX(), pos.getY(), pos.getZ(), value));
            throughput = value;
        }
        else
        {
            throughput = value;
        }
        worldObj.markBlockForUpdate(pos);
    }

    public void sendThroughputToClient(EntityPlayer player)
    {
        RFUtilities.RFU_NET_WRAPPER.sendTo(new PacketGetThroughput(pos.getX(), pos.getY(), pos.getZ(), throughput), ((EntityPlayerMP)player));
    }

    public int getThroughput()
    {
        if (worldObj.isRemote)
        {
            RFUtilities.RFU_NET_WRAPPER.sendToServer(new PacketWantThroughput(pos.getX(), pos.getY(), pos.getZ(), true));
            return throughput;
        }
        else
        {
            return throughput;
        }
    }

    public void setTransferOncePerTick(boolean once)
    {
        if (worldObj.isRemote)
        {
            RFUtilities.RFU_NET_WRAPPER.sendToServer(new PacketSetTransferMode(pos.getX(), pos.getY(), pos.getZ(), once));
            transferOncePerTick = once;
        }
        else
        {
            transferOncePerTick = once;
            worldObj.markBlockForUpdate(pos);
        }
    }

    public boolean getTransferOncePerTick()
    {
        return transferOncePerTick;
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
        throughput = nbt.getInteger("throughput");
        transferOncePerTick = nbt.getBoolean("transferMode");
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setInteger("throughput", throughput);
        nbt.setBoolean("transferMode", transferOncePerTick);
    }
}
