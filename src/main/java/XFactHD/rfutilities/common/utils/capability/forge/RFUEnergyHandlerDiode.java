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

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityDiode;
import net.minecraftforge.energy.IEnergyStorage;

public class RFUEnergyHandlerDiode implements IEnergyStorage
{
    private TileEntityDiode diode;

    public RFUEnergyHandlerDiode(TileEntityDiode diode)
    {
        this.diode = diode;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return diode.transferEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return 0;
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

    @Override
    public boolean canExtract()
    {
        return false;
    }

    @Override
    public boolean canReceive()
    {
        return true;
    }
}