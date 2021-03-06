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

package XFactHD.rfutilities.common.utils;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.items.ItemDialer;
//import cofh.thermalexpansion.item.tool.ItemMultimeter;
//import cofh.thermalexpansion.item.tool.ItemToolBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;

@SuppressWarnings("unused")
public class EventHandler
{
    public static boolean canItemShowTess = false;

    @SubscribeEvent
    public void entityEvent(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            ItemStack held = ((EntityPlayer)event.getEntity()).getHeldItemMainhand();
            canItemShowTess =
            held != null && (/*(RFUtilities.TE_LOADED && (held.getItem() instanceof ItemToolBase || held.getItem() instanceof ItemMultimeter)) || */held.getItem() instanceof ItemDialer);
        }
    }
}
