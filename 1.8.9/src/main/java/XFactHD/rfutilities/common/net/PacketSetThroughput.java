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
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.server.FMLServerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public class PacketSetThroughput implements IMessage
{
    public int x;
    public int y;
    public int z;
    public int throughtput;

    public PacketSetThroughput(){}

    public PacketSetThroughput(int x, int y, int z, int throughput)
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

    public static class HandlerPacketSetThroughput implements IMessageHandler<PacketSetThroughput, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSetThroughput message, MessageContext ctx)
        {
            World world = ctx.getServerHandler().playerEntity.worldObj;
            ((TileEntityResistor)world.getTileEntity(new BlockPos(message.x, message.y, message.z))).setThroughput(message.throughtput);
            return null;
        }
    }
}
