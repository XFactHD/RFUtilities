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

package XFactHD.rfutilities.common.blocks.block;

import XFactHD.rfutilities.RFUtilities;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public abstract class BlockBaseRFU extends Block
{
    public String name;
    private String[] subNames;

    public BlockBaseRFU(String name, Material mat, String... subNames)
    {
        super(mat);
        this.subNames = subNames;
        this.name = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MOD_ID + ":" + name);
        this.setHardness(5F);
        this.setResistance(10F);
        this.setCreativeTab(RFUtilities.creativeTab);
        GameRegistry.register(this);
    }

    protected void addItemBlock(ItemBlock itemBlock)
    {
        itemBlock.setUnlocalizedName(this.getUnlocalizedName());
        itemBlock.setRegistryName(this.getRegistryName());
        GameRegistry.register(itemBlock);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
    {
        for(int i=0; i<subNames.length; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.DOWN;
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
        AxisAlignedBB aabb = getCollisionBoundingBox(state, world, pos);
        return rayTrace(pos, start, end, aabb != null ? aabb : Block.FULL_BLOCK_AABB);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        //noinspection ConstantConditions
        return getCollisionBoundingBox(state, world, pos).offset(pos);
    }
}
