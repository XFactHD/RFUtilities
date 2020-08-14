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

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityDiode;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;

public class RFUTeslaHandlerDiode implements ITeslaConsumer, ITeslaProducer
{
    private TileEntityDiode diode;

    public RFUTeslaHandlerDiode(TileEntityDiode diode)
    {
        this.diode = diode;
    }

    @Override
    public long givePower(long amount, boolean simulate)
    {
        if (amount > Integer.MAX_VALUE)
        {
            amount = Integer.MAX_VALUE;
        }
        return diode.transferEnergy((int)amount, simulate);
    }

    @Override
    public long takePower(long amount, boolean simulate)
    {
        return 0;
    }
}