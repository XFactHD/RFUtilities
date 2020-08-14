/*  Copyright (C) <2015>  <XFactHD>

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

import XFactHD.rfutilities.common.blocks.block.BlockTransistor;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraftforge.fml.common.Optional;
//import li.cil.oc.api.machine.Arguments;
//import li.cil.oc.api.machine.Context;
//import li.cil.oc.api.network.ManagedPeripheral;
//import li.cil.oc.api.network.SidedComponent;
//import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

@Optional.InterfaceList
        ({
                @Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers", striprefs = true),
                @Optional.Interface(iface = "li.cil.oc.api.network.SidedComponent", modid = "OpenComputers", striprefs = true),
                @Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = "OpenComputers", striprefs = true)
        })
public class TileEntityTransistor extends TileEntityBaseRFU implements IEnergyReceiver, IEnergyProvider, ITickable//, SimpleComponent, SidedComponent, ManagedPeripheral
{
    private boolean isOn = false;
    private int cooldown = 0;
    private String[] methods = new String[]{"setActive", "getActive"};

    private boolean canConnectRedstone(EnumFacing facing)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockTransistor.facing).getIndex())
        {
            case 2: return (facing == EnumFacing.WEST);
            case 3: return (facing == EnumFacing.NORTH);
            case 4: return (facing == EnumFacing.EAST);
            case 5: return (facing == EnumFacing.SOUTH);
            default: return false;
        }
    }

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
            worldObj.markBlockForUpdate(pos);
        }
    }

    private Object[] call(String method, Object argument)
    {
        if ("setActive".equals(method) && argument instanceof Boolean)
        {
            setIsOn(((Boolean)argument));
            return new Object[]{null};
        }
        else if ("getActive".equals(method))
        {
            return new Object[]{getIsOn()};
        }
        return new Object[]{null};
    }

    @Override
    public void update()
    {
        if (cooldown > 0)
        {
            --this.cooldown;
        }
        EnumFacing facing = EnumFacing.UP;
        for (int i = 2; i < 6; i++)
        {
            if (canConnectRedstone(EnumFacing.getFront(i)))
            {
                facing = EnumFacing.getFront(i);
                break;
            }
        }
        boolean redstone = worldObj.isSidePowered(pos, facing);
        setIsOn(redstone);
    }

    //IEnergyHandler
    @Override
    public boolean canConnectEnergy(EnumFacing facing)
    {
        switch (worldObj.getBlockState(pos).getValue(BlockTransistor.facing).getIndex())
        {
            case 2: return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
            case 3: return (facing == EnumFacing.EAST  || facing == EnumFacing.WEST);
            case 4: return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH);
            case 5: return (facing == EnumFacing.EAST  || facing == EnumFacing.WEST);
            default: return false;
        }
    }

    @Override
    public int receiveEnergy(EnumFacing facing, int amount, boolean simulate)
    {
        EnumFacing opposite = facing.getOpposite();
        BlockPos tePos = new BlockPos(pos.getX() + opposite.getFrontOffsetX(), pos.getY(), pos.getZ() + opposite.getFrontOffsetZ());
        TileEntity te = worldObj.getTileEntity(tePos);
        if (canConnectEnergy(facing) && te instanceof IEnergyReceiver && (((IEnergyReceiver)te).receiveEnergy(facing, amount, true) > 0) && isOn)
        {
            return ((IEnergyReceiver)te).receiveEnergy(facing, amount, simulate);
        }
        else
        {
            return 0;
        }
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

    //OpenComputers
    //@Optional.Method(modid = "OpenComputers")
    //@Override
    //public String getComponentName()
    //{
    //    return "rfu_transistor";
    //}

    //@Override
    //public boolean canConnectNode(EnumFacing side)
    //{
    //    return canConnectRedstone(side);
    //}

    //@Optional.Method(modid = "OpenComputers")
    //@Override
    //public String[] methods()
    //{
    //    return methods;
    //}

    //@Optional.Method(modid = "OpenComputers")
    //@Override
    //public Object[] invoke(String method, Context context, Arguments arguments) throws Exception
    //{
    //    if (("setActive".equals(method) && arguments.count() == 1 && arguments.isBoolean(0)) || "getActive".equals(method))
    //    {
    //        return call(method, arguments.checkBoolean(0));
    //    }
    //    throw new NoSuchMethodException("Wrong argument");
    //}

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
