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
import XFactHD.rfutilities.common.utils.capability.forge.RFUEnergyHandlerDiode;
import XFactHD.rfutilities.common.utils.capability.tesla.RFUTeslaHandlerDiode;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityDiode extends TileEntityBaseRFU implements IEnergyProvider, IEnergyReceiver
{
    private RFUTeslaHandlerDiode teslaHandler = null;
    private RFUEnergyHandlerDiode energyHandler = null;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing side)
    {
        if (RFUtilities.TESLA_LOADED && capability == TeslaCapabilities.CAPABILITY_CONSUMER && side == getBlockState().getValue(BlockDiode.ORIENTATION).rotateYCCW())
        {
            return true;
        }
        else if (RFUtilities.TESLA_LOADED && capability == TeslaCapabilities.CAPABILITY_PRODUCER && side == getBlockState().getValue(BlockDiode.ORIENTATION).rotateY())
        {
            return true;
        }
        else if (capability == CapabilityEnergy.ENERGY && side == getBlockState().getValue(BlockDiode.ORIENTATION).rotateYCCW())
        {
            return true;
        }
        return super.hasCapability(capability, side);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing side)
    {
        if (RFUtilities.TESLA_LOADED && capability == TeslaCapabilities.CAPABILITY_CONSUMER && side == getBlockState().getValue(BlockDiode.ORIENTATION).rotateYCCW())
        {
            return (T)teslaHandler;
        }
        else if (RFUtilities.TESLA_LOADED && capability == TeslaCapabilities.CAPABILITY_PRODUCER && side == getBlockState().getValue(BlockDiode.ORIENTATION).rotateY())
        {
            return (T)teslaHandler;
        }
        else if (capability == CapabilityEnergy.ENERGY && side == getBlockState().getValue(BlockDiode.ORIENTATION).rotateYCCW())
        {
            return (T)energyHandler;
        }
        return super.getCapability(capability, side);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing side)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockDiode.ORIENTATION))
        {
            case NORTH: return side == EnumFacing.EAST  || side == EnumFacing.WEST;
            case SOUTH: return side == EnumFacing.WEST  || side == EnumFacing.EAST;
            case EAST:  return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
            case WEST:  return side == EnumFacing.SOUTH || side == EnumFacing.NORTH;
            default: return false;
        }
    }

    @Override
    public int receiveEnergy(EnumFacing side, int amount, boolean simulate)
    {
        if (isServerWorld() && canConnectEnergy(side))
        {
            EnumFacing facing = worldObj.getBlockState(pos).getValue(BlockDiode.ORIENTATION);
            return side == facing.rotateY() ? transferEnergy(amount, simulate) : 0;
        }
        return 0;
    }

    @Override
    public int extractEnergy(EnumFacing side, int amount, boolean simulate)
    {
        return 0;
    }

    public int transferEnergy(int amount, boolean simulate)
    {
        EnumFacing side = worldObj.getBlockState(pos).getValue(BlockDiode.ORIENTATION).rotateYCCW();
        TileEntity te = worldObj.getTileEntity(pos.offset(side));
        if (te == null) { return 0; }
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
    public void validate()
    {
        super.validate();
        if (RFUtilities.TESLA_LOADED)
        {
            teslaHandler = new RFUTeslaHandlerDiode(this);
        }
        energyHandler = new RFUEnergyHandlerDiode(this);
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

    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {

    }
}