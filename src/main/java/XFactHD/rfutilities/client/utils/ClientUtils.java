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

package XFactHD.rfutilities.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

import java.util.HashMap;

public class ClientUtils
{
    static HashMap<String, ResourceLocation> resourceMap = new HashMap<String, ResourceLocation>();

    public static Tessellator tes()
    {
        return Tessellator.instance;
    }

    public static Minecraft mc()
    {
        return Minecraft.getMinecraft();
    }

    public static void bindTexture(String path)
    {
        mc().getTextureManager().bindTexture(getResource(path));
    }

    public static void bindAtlas(int i)
    {
        mc().getTextureManager().bindTexture(i==0? TextureMap.locationBlocksTexture: TextureMap.locationItemsTexture);
    }

    public static ResourceLocation getResource(String path)
    {
        ResourceLocation rl = resourceMap.containsKey(path) ? resourceMap.get(path) : new ResourceLocation(path);
        if(!resourceMap.containsKey(path))
        {
            resourceMap.put(path, rl);
        }
        return rl;
    }

    public static WavefrontObject getModel(String path)
    {
        ResourceLocation rl = resourceMap.containsKey(path) ? resourceMap.get(path) : new ResourceLocation(path);
        if(!resourceMap.containsKey(path))
        {
            resourceMap.put(path, rl);
        }
        return (WavefrontObject) AdvancedModelLoader.loadModel(rl);
    }

    public static FontRenderer font()
    {
        return mc().fontRenderer;
    }
}
