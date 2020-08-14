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
import XFactHD.rfutilities.common.blocks.block.BlockRFMeter;
import XFactHD.rfutilities.common.net.PacketUpdateRFData;
import XFactHD.rfutilities.common.utils.capability.forge.RFUEnergyHandlerRFMeter;
import XFactHD.rfutilities.common.utils.capability.tesla.RFUTeslaHandlerRFMeter;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityRFMeter extends TileEntityBaseRFU implements ITickable, IEnergyProvider, IEnergyReceiver
{
    private RFUTeslaHandlerRFMeter teslaHandler = null;
    private RFUEnergyHandlerRFMeter energyHandler = null;
    private int lastRF = 0;
    public String lastRFDisp = "0 RF/t";
    private long transferedRF = 0;
    public String transferedRFDisp = "0 RF";

    @Override
    public void update()
    {
        if (!worldObj.isRemote)
        {
            lastRFDisp = transformLastRFScientific(lastRF);
            transferedRFDisp = transformTransferedScientific(transferedRF);
        }
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
        EnumFacing facing = getBlockState().getValue(BlockRFMeter.ORIENTATION);
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
    public int receiveEnergy(EnumFacing facing, int amount, boolean simulate)
    {
        EnumFacing opposite = facing.getOpposite();
        if (canConnectEnergy(facing))
        {
            return transferEnergy(opposite, amount, simulate);
        }
        return 0;
    }

    public int transferEnergy(EnumFacing side, int amount, boolean simulate)
    {
        int transfered = 0;
        TileEntity te = worldObj.getTileEntity(pos.offset(side));
        if (te == null) { return 0; }
        if (te instanceof IEnergyReceiver && ((IEnergyReceiver)te).receiveEnergy(side.getOpposite(), amount, true) > 0)
        {
            transfered = ((IEnergyReceiver)te).receiveEnergy(side.getOpposite(), amount, simulate);
        }
        else if (RFUtilities.TESLA_LOADED && te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()) && te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()).givePower(amount, true) > 0)
        {
            transfered = (int)te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, side.getOpposite()).givePower(amount, simulate);
        }
        else if (te.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite()) && te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(amount, true) > 0)
        {
            transfered = te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(amount, simulate);
        }
        if (!simulate)
        {
            lastRF = transfered;
            addToTransfered(transfered);
        }
        return transfered;
    }

    private String transformLastRFScientific(int value)
    {
        if (value > 1000000000)
        {
            return (value/1000000000) + "GRF/t";
        }
        else if (value > 1000000)
        {
            return (value/1000000) + " MRF/t";
        }
        else if (value > 1000)
        {
            return (value/1000) + " kRF/t";
        }
        else
        {
            return value + " RF/t";
        }
    }

    private String transformTransferedScientific(long value)
    {
        if (transferedRFDisp.equals("ERROR!"))
        {
            return transferedRFDisp;
        }
        if (value > 1000000000)
        {
            return (value/1000000000) + " GRF";
        }
        else if (value > 1000000)
        {
            return (value/1000000) + " MRF";
        }
        else if (value > 1000)
        {
            return (value/1000) + " kRF";
        }
        else
        {
            return value + " RF";
        }
    }

    private void addToTransfered(int toAdd)
    {
        if (transferedRFDisp.equals("ERROR!"))
        {
            return;
        }
        if (transferedRF == Long.MAX_VALUE || (transferedRF + toAdd) > Long.MAX_VALUE)
        {
            transferedRF = Long.MAX_VALUE;
            transferedRFDisp = "ERROR!";
            return;
        }
        transferedRF += toAdd;
        RFUtilities.RFU_NET_WRAPPER.sendToAll(new PacketUpdateRFData(pos, lastRFDisp, transferedRFDisp));
    }

    public void reset()
    {
        lastRF = 0;
        lastRFDisp = "0 RF/t";
        transferedRF = 0;
        transferedRFDisp = "0 RF";
    }

    public void updateRFData(String lastRFDisp, String transferedRFDisp)
    {
        this.lastRFDisp = lastRFDisp;
        this.transferedRFDisp = transferedRFDisp;
    }

    @Override
    public int extractEnergy(EnumFacing facing, int amount, boolean simulate)
    {
        return 0;
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
    public void validate()
    {
        super.validate();
        if (RFUtilities.TESLA_LOADED)
        {
            teslaHandler = new RFUTeslaHandlerRFMeter(this);
        }
        energyHandler = new RFUEnergyHandlerRFMeter(this);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        lastRF = nbt.getInteger("lastRF");
        transferedRF = nbt.getLong("transferedRF");
        if (descPacket)
        {
            lastRFDisp = nbt.getString("lastRFDisp");
            transferedRFDisp = nbt.getString("transferedRFDisp");
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setInteger("lastRF", lastRF);
        nbt.setLong("transferedRF", transferedRF);
        if (descPacket)
        {
            nbt.setString("lastRFDisp", lastRFDisp);
            nbt.setString("transferedRFDisp", transferedRFDisp);
        }
    }
}
