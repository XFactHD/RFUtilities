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

package XFactHD.rfutilities.common.blocks.itemBlock;

import XFactHD.rfutilities.common.blocks.block.BlockCapacitor;
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityCapacitor;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockRFCapacitor extends ItemBlock
{
    public ItemBlockRFCapacitor(Block b)
    {
        super(b);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer && block instanceof BlockCapacitor)
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("type", ((TileEntityCapacitor)world.getTileEntity(x, y, z)).type);
            stack.setTagCompound(compound);
        }
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        ItemStack capTEBasic =      new ItemStack(item, 1);
        ItemStack capTEHardened =   new ItemStack(item, 1);
        ItemStack capTEReinforced = new ItemStack(item, 1);
        ItemStack capTEResonant =   new ItemStack(item, 1);
        ItemStack capEIOBasic =     new ItemStack(item, 1);
        ItemStack capEIODouble =    new ItemStack(item, 1);
        ItemStack capEIOVibrant =   new ItemStack(item, 1);

        NBTTagCompound compoundCapTEBasic = new NBTTagCompound();
        NBTTagCompound compoundCapTEHardened = new NBTTagCompound();
        NBTTagCompound compoundCapTEReinforced = new NBTTagCompound();
        NBTTagCompound compoundCapTEResonant = new NBTTagCompound();
        NBTTagCompound compoundCapEIOBasic = new NBTTagCompound();
        NBTTagCompound compoundCapEIODouble = new NBTTagCompound();
        NBTTagCompound compoundCapEIOVibrant = new NBTTagCompound();

        compoundCapTEBasic.setInteger("type", 1);
        compoundCapTEHardened.setInteger("type", 2);
        compoundCapTEReinforced.setInteger("type", 3);
        compoundCapTEResonant.setInteger("type", 4);
        compoundCapEIOBasic.setInteger("type", 5);
        compoundCapEIODouble.setInteger("type", 6);
        compoundCapEIOVibrant.setInteger("type", 7);

        capTEBasic.setTagCompound(compoundCapTEBasic);
        capTEHardened.setTagCompound(compoundCapTEHardened);
        capTEReinforced.setTagCompound(compoundCapTEReinforced);
        capTEResonant.setTagCompound(compoundCapTEResonant);
        capEIOBasic.setTagCompound(compoundCapEIOBasic);
        capEIODouble.setTagCompound(compoundCapEIODouble);
        capEIOVibrant.setTagCompound(compoundCapEIOVibrant);

        if (Loader.isModLoaded("ThermalExpansion"))
        {
            list.add(capTEBasic);
            list.add(capTEHardened);
            list.add(capTEReinforced);
            list.add(capTEResonant);
        }
        if (Loader.isModLoaded("EnderIO"))
        {
            list.add(capEIOBasic);
            list.add(capEIODouble);
            list.add(capEIOVibrant);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
    {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("type"))
        {
            int type = stack.stackTagCompound.getInteger("type");
            if (type < 5 && !Loader.isModLoaded("ThermalExpansion"))
            {
                String warn = StatCollector.translateToLocal("desc.rfutilities:noDep.name");
                list.add(EnumChatFormatting.RED + warn);
            }
            else if (type < 5)
            {
                String info = StatCollector.translateToLocal("desc.rfutilities:capType.name");
                String typeName = StatCollector.translateToLocal("desc.rfutilities:capType_" + type + ".name");
                list.add(info + " " + typeName);
            }

            if (type > 4 && !Loader.isModLoaded("EnderIO"))
            {
                String warn = StatCollector.translateToLocal("desc.rfutilities:noDep.name");
                list.add(EnumChatFormatting.RED + warn);
            }
            else if (type > 4)
            {
                String info = StatCollector.translateToLocal("desc.rfutilities:capType.name");
                String typeName = StatCollector.translateToLocal("desc.rfutilities:capType_" + type + ".name");
                list.add(info + " " + typeName);
            }
        }
        super.addInformation(stack, player, list, bool);
    }
}
