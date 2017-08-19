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

package XFactHD.rfutilities.common.items;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.utils.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemBaseRFU extends Item
{
    public String itemName;
    public String[] subNames;
    public IIcon[][] icons;

    public ItemBaseRFU(String name, int stackSize, int iconLength, String... subNames)
    {
        this.setUnlocalizedName(Reference.MOD_ID + ":" + name);
        this.setHasSubtypes(subNames != null && subNames.length > 0);
        this.setCreativeTab(RFUtilities.creativeTab);
        this.setMaxStackSize(stackSize);
        this.itemName = name;
        this.subNames = subNames != null && subNames.length < 1 ? null : subNames;
        this.icons = new IIcon[this.subNames != null ? this.subNames.length : 1][iconLength];

        GameRegistry.registerItem(this, name);
    }

    public String[] getSubNames()
    {
        return subNames;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        if(getSubNames()!=null)
        {
            for(int i=0;i<icons.length;i++)
            {
                this.icons[i][0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + itemName + "_" + getSubNames()[i]);
            }
        }
        else
        {
            this.icons[0][0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + itemName);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        if(getSubNames() != null)
        {
            if(meta >= 0 && meta < icons.length)
            {
                return this.icons[meta][0];
            }
        }
        return icons[0][0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
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
