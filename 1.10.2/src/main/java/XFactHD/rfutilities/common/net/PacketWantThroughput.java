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

package XFactHD.rfutilities.common.net;

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityResistor;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.server.FMLServerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public class PacketWantThroughput implements IMessage
{
    public int x;
    public int y;
    public int z;
    public boolean update;

    public PacketWantThroughput(){}

    public PacketWantThroughput(int x, int y, int z, boolean update)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.update = update;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        update = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeBoolean(update);
    }

    public static class Handler implements IMessageHandler<PacketWantThroughput, IMessage>
    {
        @Override
        public IMessage onMessage(PacketWantThroughput message, MessageContext ctx)
        {
            World world = ctx.getServerHandler().playerEntity.worldObj;
            if (message.update)
            {
                ((TileEntityResistor)world.getTileEntity(new BlockPos(message.x, message.y, message.z))).sendThroughputToClient(ctx.getServerHandler().playerEntity);
            }
            return null;
        }
    }
}
