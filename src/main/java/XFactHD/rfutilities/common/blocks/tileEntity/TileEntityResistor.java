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
import XFactHD.rfutilities.common.net.PacketWantThroughput;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityResistor extends TileEntityBaseRFU implements IEnergyHandler
{
    public int throughput = 0;

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
            case 2: if (fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH) return receiveNS(fd, amount, simulate);
            case 3: if (fd == ForgeDirection.EAST || fd == ForgeDirection.WEST) return receiveEW(fd, amount, simulate);
            case 4: if (fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH) return receiveNS(fd, amount, simulate);
            case 5: if (fd == ForgeDirection.EAST || fd == ForgeDirection.WEST) return receiveEW(fd, amount, simulate);
            default: return 0;
        }
    }

    public int receiveNS(ForgeDirection fd, int amount, boolean simulate)
    {
        if (fd == ForgeDirection.NORTH && (worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof IEnergyHandler) && (((IEnergyHandler)worldObj.getTileEntity(xCoord, yCoord, zCoord + 1)).receiveEnergy(fd.getOpposite(), amount, true) > 0))
        {
            TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
            if (amount<=throughput)
            {
                return ((IEnergyHandler)te).receiveEnergy(fd.getOpposite(), amount, simulate);
            }
            else
            {
                return ((IEnergyHandler)te).receiveEnergy(fd.getOpposite(), throughput, simulate);
            }

        }
        else if (fd == ForgeDirection.SOUTH && (worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof IEnergyHandler) && (((IEnergyHandler)worldObj.getTileEntity(xCoord, yCoord, zCoord - 1)).receiveEnergy(fd.getOpposite(), amount, true) > 0))
        {
            TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
            if (amount<=throughput)
            {
                return ((IEnergyHandler)te).receiveEnergy(fd.getOpposite(), amount, simulate);
            }
            else
            {
                return ((IEnergyHandler)te).receiveEnergy(fd.getOpposite(), throughput, simulate);
            }
        }
        return 0;
    }

    public int receiveEW(ForgeDirection fd, int amount, boolean simulate)
    {
        if (fd == ForgeDirection.WEST && (worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) instanceof IEnergyHandler) && (((IEnergyHandler)worldObj.getTileEntity(xCoord + 1, yCoord, zCoord)).receiveEnergy(fd.getOpposite(), amount, true) > 0))
        {
            TileEntity te = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
            if (amount<=throughput)
            {
                return ((IEnergyHandler)te).receiveEnergy(fd.getOpposite(), amount, simulate);
            }
            else
            {
                return ((IEnergyHandler)te).receiveEnergy(fd.getOpposite(), throughput, simulate);
            }
        }
        else if (fd == ForgeDirection.EAST && (worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) instanceof IEnergyHandler) && (((IEnergyHandler)worldObj.getTileEntity(xCoord - 1, yCoord, zCoord)).receiveEnergy(fd.getOpposite(), amount, true) > 0))
        {
            TileEntity te = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
            if (amount<=throughput)
            {
                return ((IEnergyHandler)te).receiveEnergy(fd.getOpposite(), amount, simulate);
            }
            else
            {
                return ((IEnergyHandler)te).receiveEnergy(fd.getOpposite(), throughput, simulate);
            }
        }
        return 0;
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
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setInteger("throughput", throughput);
    }
}
