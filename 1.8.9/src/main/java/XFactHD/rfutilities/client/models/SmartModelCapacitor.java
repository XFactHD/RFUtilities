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

package XFactHD.rfutilities.client.models;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ISmartItemModel;

import java.util.List;

@SuppressWarnings("deprecation")
public class SmartModelCapacitor implements ISmartItemModel
{
    private List<IBakedModel> capacitors;
    private IBakedModel capacitor = null;

    public SmartModelCapacitor(List<IBakedModel> capacitors)
    {
        this.capacitors = capacitors;
    }

    @Override
    public IBakedModel handleItemState(ItemStack stack)
    {
        int type = 1;
        if (stack.hasTagCompound())
        {
            type = stack.getTagCompound().getInteger("type");
        }
        capacitor = capacitors.get(type-1);
        return capacitor;
    }

    @Override
    public List<BakedQuad> getGeneralQuads()
    {
        return capacitor.getGeneralQuads();
    }

    @Override
    public List<BakedQuad> getFaceQuads(EnumFacing side)
    {
        return capacitor.getFaceQuads(side);
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return false;
    }

    @Override
    public boolean isGui3d()
    {
        return true;
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return capacitor.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms()
    {
        return capacitor.getItemCameraTransforms();
    }
}
