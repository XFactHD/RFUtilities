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

package XFactHD.rfutilities.common.utils.capability.tesla;

import XFactHD.rfutilities.common.utils.capability.forge.RFUEnergyHandlerCapacitor;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;

public class RFUTeslaHandlerCapacitor extends BaseTeslaContainer
{
    private RFUEnergyHandlerCapacitor storage;

    public RFUTeslaHandlerCapacitor(RFUEnergyHandlerCapacitor storage)
    {
        this.storage = storage;
    }

    @Override
    public long givePower(long amount, boolean simulated)
    {
        if (amount > Integer.MAX_VALUE) { amount = Integer.MAX_VALUE; }
        long acceptedTesla = Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(), Math.min(storage.getMaxReceive(), amount));
        if(!simulated)
        {
            storage.receiveEnergy((int)amount, false);
        }
        return acceptedTesla;
    }

    @Override
    public long takePower(long amount, boolean simulated)
    {
        if (amount > Integer.MAX_VALUE) { amount = Integer.MAX_VALUE; }
        long removedPower = Math.min(storage.getEnergyStored(), Math.min(storage.getMaxExtract(), amount));
        if(!simulated)
        {
            storage.extractEnergy((int)removedPower, false);
        }
        return removedPower;
    }

    @Override
    public long getCapacity()
    {
        return storage.getMaxEnergyStored();
    }

    @Override
    public long getInputRate()
    {
        return storage.getMaxReceive();
    }

    @Override
    public long getOutputRate()
    {
        return storage.getMaxExtract();
    }

    @Override
    public long getStoredPower()
    {
        return storage.getEnergyStored();
    }
}