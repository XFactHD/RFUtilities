/*  Copyright (C) <2016>  <XFactHD, DrakoAlcarus>

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

package XFactHD.rfutilities.common.utils.compat;

import XFactHD.rfutilities.common.RFUContent;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

@JEIPlugin
@SuppressWarnings("unused")
public class JeiHelper extends BlankModPlugin
{
    @Override
    @SuppressWarnings("ConstantConditions")
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
    {
        subtypeRegistry.registerNbtInterpreter(Item.getItemFromBlock(RFUContent.blockCapacitor), new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Nullable
            @Override
            public String getSubtypeInfo(ItemStack stack)
            {
                return Integer.toString(stack.getMetadata());
            }
        });

        subtypeRegistry.registerNbtInterpreter(RFUContent.itemMaterial, new MetaDataInterpreter());
    }

    private static final class MetaDataInterpreter implements ISubtypeRegistry.ISubtypeInterpreter
    {
        @Nullable
        @Override
        public String getSubtypeInfo(ItemStack stack)
        {
            return Integer.toString(stack.getMetadata());
        }
    }
}