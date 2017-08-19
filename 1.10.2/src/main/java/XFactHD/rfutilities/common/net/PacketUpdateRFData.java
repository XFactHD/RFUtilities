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

import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityRFMeter;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateRFData implements IMessage
{
    private long pos;
    private String lastRFDisp;
    private String transferedRFDisp;

    @SuppressWarnings("unused")
    public PacketUpdateRFData()
    {

    }

    public PacketUpdateRFData(BlockPos pos, String lastRFDisp, String transferedRFDisp)
    {
        this.pos = pos.toLong();
        this.lastRFDisp = lastRFDisp;
        this.transferedRFDisp = transferedRFDisp;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        NBTTagCompound data = ByteBufUtils.readTag(buf);
        lastRFDisp = data.getString("lastRFDisp");
        transferedRFDisp = data.getString("transferedRFDisp");
        pos = data.getLong("pos");
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        NBTTagCompound data = new NBTTagCompound();
        data.setLong("pos", pos);
        data.setString("lastRFDisp", lastRFDisp);
        data.setString("trasferedRFDisp", transferedRFDisp);
        ByteBufUtils.writeTag(buf, data);
    }

    public static class Handler implements IMessageHandler<PacketUpdateRFData, IMessage>
    {
        @Override
        public IMessage onMessage(PacketUpdateRFData message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    World world = Minecraft.getMinecraft().theWorld;
                    TileEntity te = world.getTileEntity(BlockPos.fromLong(message.pos));
                    if (te instanceof TileEntityRFMeter)
                    {
                        ((TileEntityRFMeter)te).updateRFData(message.lastRFDisp, message.transferedRFDisp);
                    }
                }
            });
            return null;
        }
    }
}