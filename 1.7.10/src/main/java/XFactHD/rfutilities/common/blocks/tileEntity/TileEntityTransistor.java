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

import XFactHD.rfutilities.RFUtilities;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedPeripheral;
import li.cil.oc.api.network.SidedComponent;
import li.cil.oc.api.network.SimpleComponent;
import li.cil.oc.common.tileentity.Cable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList
        ({
                @Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers", striprefs = true),
                @Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = "OpenComputers", striprefs = true)
        })
public class TileEntityTransistor extends TileEntityBaseRFU implements IEnergyHandler, SimpleComponent, SidedComponent, ManagedPeripheral
{
    private boolean isOn = false;
    private boolean ocConnected = false;
    private String[] methods = new String[]{"setActive", "getActive"};

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

    private boolean checkOCConnected()
    {
        boolean cable = false;
        for (int i = 2; i < 6; i++)
        {
            ForgeDirection fd = ForgeDirection.getOrientation(i);
            if (canConnectRedstone(ForgeDirection.getOrientation(i)))
            {
                TileEntity te = worldObj.getTileEntity(xCoord+fd.offsetX, yCoord, zCoord+fd.offsetZ);
                cable = te instanceof Cable;
            }
        }
        return cable;
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
    public void updateEntity()
    {
        super.updateEntity();
        boolean on = isOn;
        ForgeDirection fd = ForgeDirection.UNKNOWN;
        for (int i = 2; i < 6; i++)
        {
            if (canConnectRedstone(ForgeDirection.getOrientation(i)))
            {
                fd = ForgeDirection.getOrientation(i);
                break;
            }
        }
        boolean redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

        if (RFUtilities.OC_LOADED)
        {
            ocConnected = checkOCConnected();
        }
        if (!ocConnected && on != redstone)
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
        if (("setActive".equals(method) && arguments.count() == 1 && arguments.isBoolean(0)) || "getActive".equals(method))
        {
            return call(method, arguments.checkBoolean(0));
        }
        throw new Exception("Wrong argument");
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
