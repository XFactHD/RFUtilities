/*  Copyright (C) <2016>  <XFactHD, DrakoAlcarus>

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

package XFactHD.rfutilities.common.net;

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityResistor;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class PacketSetTransferMode implements IMessage
{
    public int x;
    public int y;
    public int z;
    public boolean once;

    public PacketSetTransferMode(){}

    public PacketSetTransferMode(int x, int y, int z, boolean once)
    {
        this.x =x;
        this.y = y;
        this.z = z;
        this.once = once;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        NBTTagCompound data = ByteBufUtils.readTag(buf);
        x = data.getInteger("x");
        y = data.getInteger("y");
        z = data.getInteger("z");
        once = data.getBoolean("once");
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        NBTTagCompound data = new NBTTagCompound();
        data.setInteger("x", x);
        data.setInteger("y", y);
        data.setInteger("z", z);
        data.setBoolean("once", once);
        ByteBufUtils.writeTag(buf, data);
    }

    public static class HandlerPacketSetTransferMode implements IMessageHandler<PacketSetTransferMode, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSetTransferMode message, MessageContext ctx)
        {
            ((TileEntityResistor)ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z)).setTransferOncePerTick(message.once);
            return null;
        }
    }
}
