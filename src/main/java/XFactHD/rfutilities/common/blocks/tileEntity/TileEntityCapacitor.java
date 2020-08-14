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
import XFactHD.rfutilities.common.blocks.block.BlockCapacitor;
import XFactHD.rfutilities.common.utils.LogHelper;
import XFactHD.rfutilities.common.utils.capability.forge.RFUEnergyHandlerCapacitor;
import XFactHD.rfutilities.common.utils.capability.tesla.RFUTeslaHandlerCapacitor;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityCapacitor extends TileEntityBaseRFU implements ITickable, IEnergyProvider, IEnergyReceiver
{
    public int type = 0;
    private RFUEnergyHandlerCapacitor storage = null;
    private RFUTeslaHandlerCapacitor teslaContainer = null;
    private NBTTagCompound storageData = null;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        boolean grantAccess = false;
        if (facing == getBlockState().getValue(BlockCapacitor.ORIENTATION).rotateY())
        {
            //we are the receiver
            grantAccess = capability == CapabilityEnergy.ENERGY || (RFUtilities.TESLA_LOADED && capability == TeslaCapabilities.CAPABILITY_CONSUMER);
        }
        else if (facing == getBlockState().getValue(BlockCapacitor.ORIENTATION).rotateYCCW())
        {
            //we are the provider
            grantAccess = capability == CapabilityEnergy.ENERGY || (RFUtilities.TESLA_LOADED && capability == TeslaCapabilities.CAPABILITY_CONSUMER);
        }
        return grantAccess || super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (facing == getBlockState().getValue(BlockCapacitor.ORIENTATION).rotateY())
        {
            //we are the receiver
            if (capability == CapabilityEnergy.ENERGY)
            {
                return (T)storage.setReceiver(true);
            }
            else if (RFUtilities.TESLA_LOADED && capability == TeslaCapabilities.CAPABILITY_CONSUMER)
            {
                return (T)teslaContainer;
            }
        }
        else if (facing == getBlockState().getValue(BlockCapacitor.ORIENTATION).rotateYCCW())
        {
            //we are the provider
            if (capability == CapabilityEnergy.ENERGY)
            {
                return (T)storage.setReceiver(false);
            }
            else if (RFUtilities.TESLA_LOADED && capability == TeslaCapabilities.CAPABILITY_PRODUCER)
            {
                return (T)teslaContainer;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing side)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockCapacitor.ORIENTATION))
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
        if (!canConnectEnergy(side))
        {
            return 0;
        }
        switch (worldObj.getBlockState(pos).getValue(BlockCapacitor.ORIENTATION))
        {
            case NORTH: if(side == EnumFacing.EAST)  return 0; //storage.receiveEnergy(amount, simulate);
            case SOUTH: if(side == EnumFacing.WEST)  return 0; //storage.receiveEnergy(amount, simulate);
            case EAST:  if(side == EnumFacing.SOUTH) return 0; //storage.receiveEnergy(amount, simulate);
            case WEST:  if(side == EnumFacing.NORTH) return 0; //storage.receiveEnergy(amount, simulate);
            default: return 0;
        }
    }

    @Override
    public int extractEnergy(EnumFacing side, int amount, boolean simulate)
    {
        if (!canConnectEnergy(side))
        {
            return 0;
        }
        if (side.rotateYCCW() == getBlockState().getValue(BlockCapacitor.ORIENTATION))
        {
            return storage.extractEnergy(amount, simulate);
        }
        return 0;
    }

    @Override
    public int getEnergyStored(EnumFacing facing)
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing facing)
    {
        return storage.getMaxEnergyStored();
    }

    @Override
    public void update()
    {
		if (storage.getMaxEnergyStored() != storageByType())
        {
            storage.setCapacity(storageByType());
            storage.setMaxReceive(receiveByType());
            storage.setMaxExtract(extractByType());
        }
        transferPower(getBlockState());
    }

    @SuppressWarnings("ConstantConditions")
    private int transferPower(IBlockState state)
    {
        if (!isServerWorld())
        {
            return 0;
        }
        EnumFacing side = state.getValue(BlockCapacitor.ORIENTATION).rotateYCCW();
        TileEntity te = worldObj.getTileEntity(pos.offset(side));
        if (te == null || side == null) { return 0; }
        if (te instanceof IEnergyReceiver && ((IEnergyReceiver)te).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), true), true) > 0)
        {
            return ((IEnergyReceiver)te).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxExtract(), false), false);
        }
        else if (RFUtilities.TESLA_LOADED && te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()) && te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()).givePower(storage.extractEnergy(storage.getMaxExtract(), true), true) > 0)
        {
            return (int)te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()).givePower(storage.extractEnergy(storage.getMaxExtract(), false), false);
        }
        else if (te.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite()) && te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(storage.extractEnergy(storage.getMaxExtract(), true), true) > 0)
        {
            return te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(storage.extractEnergy(storage.getMaxExtract(), false), false);
        }
        return 0;
    }

    public int storageByType()
    {
        switch (type)
        {
            case 1: return 80000;
            case 2: return 400000;
            case 3: return 4000000;
            case 4: return 20000000;
            case 5: return 100000;
            case 6: return 200000;
            case 7: return 500000;
            default: return 0;
        }
    }

    public int receiveByType()
    {
        switch (type)
        {
            case 1: return 200;
            case 2: return 800;
            case 3: return 8000;
            case 4: return 32000;
            case 5: return 200;
            case 6: return 600;
            case 7: return 1000;
            default: return 0;
        }
    }

    public int extractByType()
    {
        switch (type)
        {
            case 1: return 80;
            case 2: return 400;
            case 3: return 4000;
            case 4: return 16000;
            case 5: return 200;
            case 6: return 400;
            case 7: return 800;
            default: return 0;
        }
    }

    @Override
    public void validate()
    {
        storage = new RFUEnergyHandlerCapacitor(this);
        storage.setCapacity(storageByType());
        storage.setMaxReceive(receiveByType());
        storage.setMaxExtract(extractByType());
        if (storageData != null)
        {
            storage.readFromNBT(storageData);
        }
        if (RFUtilities.TESLA_LOADED)
        {
            teslaContainer = new RFUTeslaHandlerCapacitor(storage);
        }
        super.validate();
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        type = nbt.getInteger("type");
        if (storage != null)
        {
            storage.readFromNBT(nbt);
        }
        else
        {
            storageData = nbt;
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setInteger("type", type);
        storage.writeToNBT(nbt);
    }
}