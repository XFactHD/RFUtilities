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

package XFactHD.rfutilities.common.blocks.block;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.utils.Reference;
import cofh.api.block.IDismantleable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockBaseRFU extends BlockContainer implements IDismantleable
{
    public String name;
    public String[] subNames;
    public final IIcon[][] icons;
    protected final int iconDimensions;

    public BlockBaseRFU(String name, Material mat, int iconDimensions, Class<? extends ItemBlock> itemblock, String... subNames)
    {
        super(mat);
        this.subNames = subNames;
        this.name = name;
        this.icons = new IIcon[subNames.length][iconDimensions];
        this.iconDimensions = iconDimensions;
        this.setBlockName(Reference.MOD_ID + ":" + name);
        GameRegistry.registerBlock(this, itemblock, name);
        this.setCreativeTab(RFUtilities.creativeTab);
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for(int i=0; i<subNames.length; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        for(int i=0;i<subNames.length;i++)
            icons[i][0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + name + "_" + subNames[i]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if(meta<icons.length)
        {
            return icons[meta][getSideForTexture(side)];
        }
        return null;
    }

    protected int getSideForTexture(int side)
    {
        if(iconDimensions==2)
        {
            return side==0||side==1?0: 1;
        }
        if(iconDimensions==4)
        {
            return side<2?side: side==2||side==3?2: 3;
        }
        return Math.min(side, iconDimensions-1);
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public boolean canDismantle(EntityPlayer entityPlayer, World world, int i, int i1, int i2)
    {
        return false;
    }

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer entityPlayer, World world, int x, int y, int z, boolean b)
    {
        return null;
    }
}
