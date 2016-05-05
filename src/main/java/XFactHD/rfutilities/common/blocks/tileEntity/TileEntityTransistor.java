/*  Copyright (C) <2015>  <XFactHD, DrakoAlcarus>

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

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedPeripheral;
import li.cil.oc.api.network.SidedComponent;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList
        ({
                @Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers", striprefs = true),
                @Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = "OpenComputers", striprefs = true),
                @Optional.Interface(iface = "li.cil.oc.api.network.SidedComponent", modid = "OpenComputers", striprefs = true)
        })
public class TileEntityTransistor extends TileEntityBaseRFU implements IEnergyHandler, SimpleComponent, SidedComponent, ManagedPeripheral
{
    private boolean isOn = false;
    private String mode = "rs";
    private String[] methods = new String[]{"setActive", "getActive", "setMode", "getMode"};

    public boolean canConnectRedstone(ForgeDirection fd)
    {
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
        {
            case 2: return (fd == ForgeDirection.WEST);
            case 3: return (fd == ForgeDirection.NORTH);
            case 4: return (fd == ForgeDirection.EAST);
            case 5: return (fd == ForgeDirection.SOUTH);
            default: return false;
        }
    }

    public boolean getIsOn()
    {
        return isOn;
    }

    private void setIsOn(boolean on)
    {
        isOn = on;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    private Object[] call(String method, Object argument)
    {
        if ("setActive".equals(method) && argument instanceof Boolean)
        {
            setIsOn((Boolean)argument);
            return new Object[]{null};
        }
        else if ("getActive".equals(method))
        {
            return new Object[]{getIsOn()};
        }
        else if ("setMode".equals(method) && argument instanceof String && ("rs".equals(argument) || "oc".equals(argument)))
        {
            setMode((String)argument);
            return new Object[]{null};
        }
        else if ("getMode".equals(method))
        {
            return new Object[]{getMode()};
        }
        return new Object[]{"Throw!"};
    }

    private void setMode(String mode)
    {
        this.mode = mode;
    }

    private String getMode()
    {
        return mode;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        boolean on = isOn;
        boolean redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
        if ("rs".equals(getMode()) && on != redstone)
        {
            setIsOn(redstone);
        }
    }

    //IEnergyHandler
    @Override
    public boolean canConnectEnergy(ForgeDirection fd)
    {
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord))
        {
            case 2: return (fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH);
            case 3: return (fd == ForgeDirection.EAST || fd == ForgeDirection.WEST);
            case 4: return (fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH);
            case 5: return (fd == ForgeDirection.EAST || fd == ForgeDirection.WEST);
            default: return false;
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection fd, int amount, boolean simulate)
    {
        ForgeDirection opposite = fd.getOpposite();
        TileEntity te = worldObj.getTileEntity(xCoord + opposite.offsetX, yCoord, zCoord + opposite.offsetZ);
        if (canConnectEnergy(fd) && te instanceof IEnergyReceiver && (((IEnergyReceiver)te).receiveEnergy(fd, amount, true) > 0) && isOn)
        {
            return ((IEnergyReceiver)te).receiveEnergy(fd, amount, simulate);
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int extractEnergy(ForgeDirection fd, int amount, boolean simulate)
    {
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection fd)
    {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection fd)
    {
        return 0;
    }

    //OpenComputers
    @Optional.Method(modid = "OpenComputers")
    @Override
    public String getComponentName()
    {
        return "rfu_transistor";
    }

    @Override
    public boolean canConnectNode(ForgeDirection fd)
    {
        return canConnectRedstone(fd);
    }

    @Optional.Method(modid = "OpenComputers")
    @Override
    public String[] methods()
    {
        return methods;
    }

    @Optional.Method(modid = "OpenComputers")
    @Override
    public Object[] invoke(String method, Context context, Arguments arguments) throws Exception
    {
        if (("setActive".equals(method) && arguments.count() == 1 && arguments.isBoolean(0)) || ("setMode".equals(method) && arguments.count() == 1 && arguments.isString(0)) || ("getActive".equals(method) && arguments.count() == 0) || ("getMode".equals(method) && arguments.count() == 0))
        {
            Object[] result = call(method, arguments.checkAny(0));
            if (result[0] != null && result[0].equals("Throw!"))
            {
                throw new NoSuchMethodException("Wrong argument!");
            }
            return result;
        }
        throw new NoSuchMethodException("Wrong argument!");
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
