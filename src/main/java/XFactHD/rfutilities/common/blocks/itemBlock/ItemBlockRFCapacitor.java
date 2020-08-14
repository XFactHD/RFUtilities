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

package XFactHD.rfutilities.common.blocks.itemBlock;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.blocks.block.BlockCapacitor;
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityCapacitor;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("unchecked")
public class ItemBlockRFCapacitor extends ItemBlock
{
    public ItemBlockRFCapacitor(Block b)
    {
        super(b);
        setHasSubtypes(true);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity)
    {
        TileEntity te = world.getTileEntity(pos);
        if (entity instanceof EntityPlayer && block instanceof BlockCapacitor && te instanceof TileEntityCapacitor)
        {
            stack.setItemDamage(((TileEntityCapacitor)te).type);
        }
        return super.onBlockDestroyed(stack, world, state, pos, entity);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
        list.add(new ItemStack(item, 1, 3));
        list.add(new ItemStack(item, 1, 4));
        list.add(new ItemStack(item, 1, 5));
        list.add(new ItemStack(item, 1, 6));
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
    {
        super.addInformation(stack, player, list, bool);
        //FIXME: just a workaround for an issue with JEI
        if (!stack.hasTagCompound()) { return; }
        int type = stack.getMetadata()+1;
        if (type < 5)
        {
            String info = I18n.format("desc.rfutilities:capType.name");
            String typeName = I18n.format("desc.rfutilities:capType_" + type + ".name");
            list.add(info + " " + typeName);
            if (!Loader.isModLoaded("ThermalExpansion") && !RFUtilities.devEnv)
            {
                String warn = I18n.format("desc.rfutilities:noDep.name");
                list.add(TextFormatting.RED + warn);
            }
        }
        else if (type > 4)
        {
            String info = I18n.format("desc.rfutilities:capType.name");
            String typeName = I18n.format("desc.rfutilities:capType_" + type + ".name");
            list.add(info + " " + typeName);
            if (!Loader.isModLoaded("EnderIO") && !RFUtilities.devEnv)
            {
                String warn = I18n.format("desc.rfutilities:noDep.name");
                list.add(TextFormatting.RED + warn);
            }
        }
    }
}