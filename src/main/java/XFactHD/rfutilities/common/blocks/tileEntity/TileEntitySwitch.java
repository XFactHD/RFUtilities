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

package XFactHD.rfutilities.common.blocks.tileEntity;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.blocks.block.BlockDiode;
import XFactHD.rfutilities.common.blocks.block.BlockSwitch;
import XFactHD.rfutilities.common.utils.capability.forge.RFUEnergyHandlerSwitch;
import XFactHD.rfutilities.common.utils.capability.tesla.RFUTeslaHandlerSwitch;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntitySwitch extends TileEntityBaseRFU implements IEnergyReceiver, IEnergyProvider
{
    private RFUTeslaHandlerSwitch teslaHandler = null;
    private RFUEnergyHandlerSwitch energyHandler = null;
    private boolean isOn = false;

    public boolean getIsOn()
    {
        return isOn;
    }

    public void setIsOn(boolean status)
    {
        isOn = status;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY || (RFUtilities.TESLA_LOADED && (capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_CONSUMER)))
        {
            return canConnectEnergy(facing);
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY && canConnectEnergy(facing))
        {
            return (T)energyHandler.setSendingSide(facing.getOpposite());
        }
        else if (RFUtilities.TESLA_LOADED && (capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_CONSUMER))
        {
            return (T)teslaHandler.setSendingSide(facing.getOpposite());
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing side)
    {
        EnumFacing facing = getBlockState().getValue(BlockSwitch.ORIENTATION);
        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
        {
            return side == EnumFacing.EAST || side == EnumFacing.WEST;
        }
        else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
        {
            return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
        }
        return false;
    }

    @Override
    public int receiveEnergy(EnumFacing side, int amount, boolean simulate)
    {
        EnumFacing opposite = side.getOpposite();
        return transferEnergy(opposite, amount, simulate);
    }

    public int transferEnergy(EnumFacing side, int amount, boolean simulate)
    {
        TileEntity te = worldObj.getTileEntity(pos.offset(side));
        if (!isOn || te == null) { return 0; }
        if (te instanceof IEnergyReceiver && ((IEnergyReceiver)te).receiveEnergy(side.getOpposite(), amount, true) > 0)
        {
            return ((IEnergyReceiver)te).receiveEnergy(side.getOpposite(), amount, simulate);
        }
        else if (RFUtilities.TESLA_LOADED && te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()) && te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()).givePower(amount, true) > 0)
        {
            return (int)te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()).givePower(amount, simulate);
        }
        else if (te.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite()) && te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(amount, true) > 0)
        {
            return te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(amount, simulate);
        }
        return 0;
    }

    @Override
    public int extractEnergy(EnumFacing side, int amount, boolean simulate)
    {
        return 0;
    }

    @Override
    public int getEnergyStored(EnumFacing side)
    {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing side)
    {
        return 0;
    }

    @Override
    public void validate()
    {
        super.validate();
        if (RFUtilities.TESLA_LOADED)
        {
            teslaHandler = new RFUTeslaHandlerSwitch(this);
        }
        energyHandler = new RFUEnergyHandlerSwitch(this);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        isOn = nbt.getBoolean("isOn");
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setBoolean("isOn", isOn);
    }
}
