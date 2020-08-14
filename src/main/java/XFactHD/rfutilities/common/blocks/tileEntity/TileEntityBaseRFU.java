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

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

abstract class TileEntityBaseRFU extends TileEntity
{
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.readCustomNBT(nbt, false);
    }

    public abstract void readCustomNBT(NBTTagCompound nbt, boolean descPacket);

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        this.writeCustomNBT(nbt, false);
        return nbt;
    }

    public abstract void writeCustomNBT(NBTTagCompound nbt, boolean descPacket);

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeCustomNBT(nbttagcompound, true);
        return new SPacketUpdateTileEntity(this.pos, 3, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readCustomNBT(pkt.getNbtCompound(), true);
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound nbt = super.getUpdateTag();
        writeCustomNBT(nbt, true);
        return nbt;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        readCustomNBT(tag, true);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    protected boolean isServerWorld()
    {
        return !worldObj.isRemote;
    }

    protected IBlockState getBlockState()
    {
        return worldObj.getBlockState(pos);
    }
}