/*  Copyright (C) <2016>  <XFactHD>

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

package XFactHD.rfutilities.common.utils.capability.forge;

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityCapacitor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class RFUEnergyHandlerCapacitor extends EnergyStorage
{
    private TileEntityCapacitor capacitor;
    private boolean receiver;

    public RFUEnergyHandlerCapacitor(TileEntityCapacitor capacitor)
    {
        super(capacitor.storageByType(), capacitor.receiveByType(), capacitor.extractByType());
        this.capacitor = capacitor;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public void setMaxReceive(int maxReceive)
    {
        this.maxReceive = maxReceive;
    }

    public void setMaxExtract(int maxExtract)
    {
        this.maxExtract = maxExtract;
    }

    public int getMaxExtract()
    {
        return maxExtract;
    }

    public int getMaxReceive()
    {
        return maxReceive;
    }

    public RFUEnergyHandlerCapacitor setReceiver(boolean receiver)
    {
        this.receiver = receiver;
        return this;
    }

    @Override
    public boolean canExtract()
    {
        return !receiver;
    }

    @Override
    public boolean canReceive()
    {
        return receiver;
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        if (energy < 0)
        {
            energy = 0;
        }
        nbt.setInteger("Energy", energy);
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        this.energy = nbt.getInteger("Energy");

        if (energy > capacity)
        {
            energy = capacity;
        }
    }
}