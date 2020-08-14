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

package XFactHD.rfutilities.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemDialer extends ItemBaseRFU
{
    public ItemDialer()
    {
        super("itemDialer", 1, 2, "Dialer");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
    {
        if (stack.hasTagCompound())
        {
            String mode = stack.getTagCompound().getBoolean("modeDial") ? StatCollector.translateToLocal("desc.rfutilities:modeDial.name") : StatCollector.translateToLocal("desc.rfutilities:modeClear.name");
            list.add(mode);

            if (stack.getTagCompound().hasKey("senderX"))
            {
                int x = stack.getTagCompound().getInteger("senderX");
                int y = stack.getTagCompound().getInteger("senderY");
                int z = stack.getTagCompound().getInteger("senderZ");
                String dialed = StatCollector.translateToLocal("desc.rfutilities:dialedTo.name") + " " + StatCollector.translateToLocal("desc.rfutilities:tessAt.name") + " X=" + x + ", Y=" + y + ", Z=" + z + ".";
                list.add(EnumChatFormatting.GREEN + dialed);
            }
            else
            {
                String notDialed = EnumChatFormatting.RED + StatCollector.translateToLocal("desc.rfutilities:dialedTo.name") + " " + StatCollector.translateToLocal("desc.rfutilities:nothing.name");
                list.add(EnumChatFormatting.RED + notDialed);
            }
        }
        super.addInformation(stack, player, list, b);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote && player.isSneaking() && stack.getTagCompound().getBoolean("modeDial"))
        {
            stack.setTagCompound(null);
            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("modeDial", true);
            stack.setTagCompound(compound);
            player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("chat.rfutilities:clearedDialer.name")));
        }
        return stack;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack stack = new ItemStack(item, 1, 0);
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("modeDial", true);
        stack.setTagCompound(compound);
        list.add(stack);
    }
}