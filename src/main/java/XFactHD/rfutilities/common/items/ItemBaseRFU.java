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

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBaseRFU extends Item
{
    private String itemName;
    private String[] subNames;

    public ItemBaseRFU(String name, int stackSize, String... subNames)
    {
        this.setUnlocalizedName(Reference.MOD_ID + ":" + name);
        this.setRegistryName(name);
        this.setHasSubtypes(subNames != null && subNames.length > 0);
        this.setCreativeTab(RFUtilities.creativeTab);
        this.setMaxStackSize(stackSize);
        this.itemName = name;
        this.subNames = subNames != null && subNames.length < 1 ? null : subNames;

        GameRegistry.register(this);
    }

    public String[] getSubNames()
    {
        return subNames;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list)
    {
        if(getSubNames() != null)
        {
            for(int i=0;i < getSubNames().length; i++)
            {
                list.add(new ItemStack(this,1,i));
            }
        }
        else
        {
            list.add(new ItemStack(this));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if(getSubNames()!=null)
        {
            String subName = stack.getItemDamage()<getSubNames().length?getSubNames()[stack.getItemDamage()]:"";
            return this.getUnlocalizedName()+"."+subName;
        }
        return this.getUnlocalizedName();
    }
}
