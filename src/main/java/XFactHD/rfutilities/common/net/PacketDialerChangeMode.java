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

package XFactHD.rfutilities.common.net;

import XFactHD.rfutilities.common.items.ItemDialer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class PacketDialerChangeMode implements IMessage
{
    public PacketDialerChangeMode(){}

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    public static class HandlerPacketDialerChangeMode implements IMessageHandler<PacketDialerChangeMode, IMessage>
    {
        @Override
        public IMessage onMessage(PacketDialerChangeMode message, MessageContext ctx)
        {
            ItemStack dialer = null;
            InventoryPlayer inv = ctx.getServerHandler().playerEntity.inventory;
            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() instanceof ItemDialer)
                {
                    dialer = inv.getStackInSlot(i);// FIXME: Threadedness!!!
                    dialer.getTagCompound().setBoolean("modeDial", !dialer.getTagCompound().getBoolean("modeDial"));
                    break;
                }
            }
            return null;
        }
    }
}
