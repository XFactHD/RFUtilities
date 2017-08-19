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

package XFactHD.rfutilities.common.net;

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityResistor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public class PacketGetThroughput implements IMessage
{
    private int x;
    private int y;
    private int z;
    private int throughtput;

    public PacketGetThroughput(){}

    public PacketGetThroughput(int x, int y, int z,int throughput)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.throughtput = throughput;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        throughtput = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(throughtput);
    }

    public static class HandlerPacketGetThroughput implements IMessageHandler<PacketGetThroughput, IMessage>
    {
        @Override
        public IMessage onMessage(PacketGetThroughput message, MessageContext ctx)
        {
            World world = FMLClientHandler.instance().getWorldClient();
            ((TileEntityResistor)world.getTileEntity(message.x, message.y, message.z)).setThroughput(message.throughtput);
            return null;
        }
    }
}
