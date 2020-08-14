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

package XFactHD.rfutilities.common.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Utils
{
    public static ItemStack copyStackWithAmount(ItemStack stack, int amount)
    {
        if(stack==null)
            return null;
        ItemStack s2 = stack.copy();
        s2.stackSize=amount;
        return s2;
    }

    public static TileEntity getExistingTileEntity(World world, int x, int y, int z)
    {
        if(world.blockExists(x, y, z))
            return world.getTileEntity(x, y, z);
        return null;
    }

    public static int screenHeight()
    {
        return Minecraft.getMinecraft().displayHeight;
    }

    public static int screenWidth()
    {
        return Minecraft.getMinecraft().displayWidth;
    }
}
