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
import XFactHD.rfutilities.common.blocks.block.BlockTransistor;
import XFactHD.rfutilities.common.utils.capability.forge.RFUEnergyHandlerTransistor;
import XFactHD.rfutilities.common.utils.capability.tesla.RFUTeslaHandlerTransistor;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityTransistor extends TileEntityBaseRFU implements IEnergyReceiver, IEnergyProvider, ITickable
{
    private RFUTeslaHandlerTransistor teslaHandler = null;
    private RFUEnergyHandlerTransistor energyHandler = null;
    private boolean isOn = false;
    private int cooldown = 0;
    private EnumFacing facing = null;

    public boolean getIsOn()
    {
        return isOn;
    }

    private void setIsOn(boolean on)
    {
        if (on != isOn && cooldown == 0)
        {
            isOn = on;
            this.cooldown = 5;
            worldObj.scheduleUpdate(pos, worldObj.getBlockState(pos).getBlock(), 0);
            worldObj.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    private EnumFacing getFacing()
    {
        if (facing == null)
        {
            facing = getBlockState().getValue(BlockTransistor.ORIENTATION);
        }
        return facing;
    }

    public void setFacing(EnumFacing facing)
    {
        this.facing = facing;
    }

    @Override
    public void update()
    {
        if (cooldown > 0)
        {
            --this.cooldown;
        }
        boolean redstone = getPowerOnSide(getFacing()) > 0;
        setIsOn(redstone);
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

    //IEnergyHandler
    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockTransistor.ORIENTATION).getIndex())
        {
            case 2: return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
            case 3: return (facing == EnumFacing.EAST  || facing == EnumFacing.WEST);
            case 4: return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
            case 5: return (facing == EnumFacing.EAST  || facing == EnumFacing.WEST);
            default: return false;
        }
    }

    @Override
    public int receiveEnergy(EnumFacing side, int amount, boolean simulate)
    {
        EnumFacing opposite = facing.getOpposite();
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

    private int getPowerOnSide(EnumFacing side)
    {
        IBlockState state = worldObj.getBlockState(pos.offset(side.getOpposite()));
        Block block = state.getBlock();
        if (block == Blocks.REDSTONE_BLOCK)
        {
            return 15;
        }
        else if (block == Blocks.REDSTONE_WIRE)
        {
            return state.getValue(BlockRedstoneWire.POWER);
        }
        else if (block == Blocks.LEVER)
        {
            return state.getValue(BlockLever.POWERED) ? 15 : 0;
        }
        else if (block instanceof BlockPressurePlateWeighted)
        {
            return state.getValue(BlockPressurePlateWeighted.POWER) > 1 ? 15 : 0;
        }
        else if (block instanceof BlockPressurePlate)
        {
            return state.getValue(BlockPressurePlate.POWERED) ? 15 : 0;
        }
        else
        {
            return worldObj.getStrongPower(pos, side);
        }
    }

    @Override
    public void validate()
    {
        super.validate();
        if (RFUtilities.TESLA_LOADED)
        {
            teslaHandler = new RFUTeslaHandlerTransistor(this);
        }
        energyHandler = new RFUEnergyHandlerTransistor(this);
    }

    //NBT
    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        nbt.setBoolean("isOn", isOn);
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
    {
        isOn = nbt.getBoolean("isOn");
    }
}
