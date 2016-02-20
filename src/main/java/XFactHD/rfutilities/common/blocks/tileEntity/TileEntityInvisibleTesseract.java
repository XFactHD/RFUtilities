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
import cofh.api.transport.IEnderEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityInvisibleTesseract extends TileEntityBaseRFU implements IEnderEnergyHandler, IEnergyHandler
{
    public boolean blur = false;
    public boolean active = false;

    @Override
    public boolean canConnectEnergy(ForgeDirection fd)
    {
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
        {
            case 2: return (fd == ForgeDirection.WEST);
            case 3: return (fd == ForgeDirection.NORTH);
            case 4: return (fd == ForgeDirection.EAST);
            case 5: return (fd == ForgeDirection.SOUTH);
            default: return false;
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int amount, boolean simulate)
    {
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b)
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
    public int getFrequency()
    {
        return 0;
    }

    @Override
    public boolean setFrequency(int freq)
    {
        return false;
    }

    @Override
    public boolean clearFrequency()
    {
        return false;
    }

    @Override
    public String getChannelString()
    {
        return null;
    }

    @Override
    public boolean canSendEnergy()
    {
        return false;
    }

    @Override
    public boolean canReceiveEnergy()
    {
        return false;
    }

    @Override
    public int receiveEnergy(int amount, boolean simulate)
    {
        return 0;
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        blur = nbt.getBoolean("blur");
        active = nbt.getBoolean("active");
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setBoolean("blur", blur);
        nbt.setBoolean("active", active);
    }
}
