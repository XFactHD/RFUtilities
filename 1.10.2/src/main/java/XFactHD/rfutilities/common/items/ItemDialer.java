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

package XFactHD.rfutilities.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemDialer extends ItemBaseRFU
{
    public ItemDialer()
    {
        super("itemDialer", 1, "Dialer");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b)
    {
        if (stack.hasTagCompound())
        {
            //noinspection ConstantConditions
            String mode = stack.getTagCompound().getBoolean("modeDial") ? I18n.format("desc.rfutilities:modeDial.name") : I18n.format("desc.rfutilities:modeClear.name");
            list.add(mode);

            if (stack.getTagCompound().hasKey("senderX"))
            {
                int x = stack.getTagCompound().getInteger("senderX");
                int y = stack.getTagCompound().getInteger("senderY");
                int z = stack.getTagCompound().getInteger("senderZ");
                String dialed = I18n.format("desc.rfutilities:dialedTo.name") + " " + I18n.format("desc.rfutilities:tessAt.name") + " X=" + x + ", Y=" + y + ", Z=" + z + ".";
                list.add(TextFormatting.GREEN + dialed);
            }
            else
            {
                String notDialed = TextFormatting.RED + I18n.format("desc.rfutilities:dialedTo.name") + " " + I18n.format("desc.rfutilities:nothing.name");
                list.add(TextFormatting.RED + notDialed);
            }
        }
        super.addInformation(stack, player, list, b);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        //noinspection ConstantConditions
        if (!world.isRemote && player.isSneaking() && stack.getTagCompound().getBoolean("modeDial"))
        {
            //noinspection ConstantConditions
            stack.setTagCompound(null);
            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("modeDial", true);
            stack.setTagCompound(compound);
            player.addChatComponentMessage(new TextComponentTranslation("chat.rfutilities:clearedDialer.name"));
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list)
    {
        ItemStack stack = new ItemStack(item, 1, 0);
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("modeDial", true);
        stack.setTagCompound(compound);
        list.add(stack);
    }
}