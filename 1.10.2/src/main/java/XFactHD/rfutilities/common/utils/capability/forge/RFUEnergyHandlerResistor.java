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

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityResistor;
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntitySwitch;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public class RFUEnergyHandlerResistor implements IEnergyStorage
{
    private TileEntityResistor resistor;
    private EnumFacing side = null;

    public RFUEnergyHandlerResistor(TileEntityResistor resistor)
    {
        this.resistor = resistor;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return resistor.transferEnergy(side, maxReceive, simulate);
    }

    public RFUEnergyHandlerResistor setSendingSide(EnumFacing side)
    {
        this.side = side;
        return this;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return 0;
    }

    @Override
    public boolean canReceive()
    {
        return true;
    }

    @Override
    public boolean canExtract()
    {
        return false;
    }

    @Override
    public int getEnergyStored()
    {
        return 0;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return 0;
    }
}