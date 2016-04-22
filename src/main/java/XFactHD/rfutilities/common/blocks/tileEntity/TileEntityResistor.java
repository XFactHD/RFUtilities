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
import XFactHD.rfutilities.common.net.PacketGetThroughput;
import XFactHD.rfutilities.common.net.PacketSetThroughput;
import XFactHD.rfutilities.common.net.PacketSetTransferMode;
import XFactHD.rfutilities.common.net.PacketWantThroughput;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cofh.thermalexpansion.block.cell.TileCellCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityResistor extends TileEntityBaseRFU implements IEnergyHandler
{
    public int throughput = 0;
    private boolean transferOncePerTick = false;
    //private boolean wait = false;

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
    public void updateEntity()
    {
        super.updateEntity();
        //wait = false;
    }

    @Override
    public int receiveEnergy(ForgeDirection fd, int amount, boolean simulate)
    {
        //if (transferOncePerTick && !wait)
        //{
        //    wait = true;
        //}

        ForgeDirection opposite = fd.getOpposite();
        TileEntity te = worldObj.getTileEntity(xCoord + opposite.offsetX, yCoord, zCoord + opposite.offsetZ);
        if (canConnectEnergy(fd) && te instanceof IEnergyReceiver && (((IEnergyReceiver)te).receiveEnergy(fd, amount, true) > 0))
        {
            if (amount<=throughput)
            {
                return ((IEnergyReceiver)te).receiveEnergy(fd, amount, simulate);
            }
            else
            {
                return ((IEnergyReceiver)te).receiveEnergy(fd, throughput, simulate);
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
            RFUtilities.RFU_NET_WRAPPER.sendToServer(new PacketSetThroughput(xCoord, yCoord, zCoord, value));
            throughput = value;
        }
        else
        {
            throughput = value;
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public void sendThroughputToClient(EntityPlayer player)
    {
        RFUtilities.RFU_NET_WRAPPER.sendTo(new PacketGetThroughput(xCoord, yCoord, zCoord, throughput), ((EntityPlayerMP)player));
    }

    public int getThroughput()
    {
        if (worldObj.isRemote)
        {
            RFUtilities.RFU_NET_WRAPPER.sendToServer(new PacketWantThroughput(xCoord, yCoord, zCoord, true));
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
            RFUtilities.RFU_NET_WRAPPER.sendToServer(new PacketSetTransferMode(xCoord, yCoord, zCoord, once));
            transferOncePerTick = once;
        }
        else
        {
            transferOncePerTick = once;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public boolean getTransferOncePerTick()
    {
        return transferOncePerTick;
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
