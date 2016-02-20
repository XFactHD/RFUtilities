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

package XFactHD.rfutilities.client.render.block;

import XFactHD.rfutilities.client.models.ModelInvisibleTesseract;
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityInvisibleTesseract;
import XFactHD.rfutilities.common.utils.Reference;
import cofh.thermalexpansion.item.tool.ItemWrench;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderInvisTesseract extends TileEntitySpecialRenderer
{
    private ModelInvisibleTesseract model;

    public RenderInvisTesseract()
    {
        this.model = new ModelInvisibleTesseract();
    }

    private void adjustRotatePivotViaMeta(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        GL11.glPushMatrix();
        GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        if ((Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemWrench) || !((TileEntityInvisibleTesseract)te).blur)
        {
            final ResourceLocation textureLocation = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/models/modelInvisTess" + (((TileEntityInvisibleTesseract)te).active ? "_On" : "_Off") + ".png");
            bindTexture(textureLocation);
        }
        else
        {
            final ResourceLocation textureLocation = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/models/modelInvisTessBlur.png");
            bindTexture(textureLocation);
        }

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        int rotation = 0;
        switch (te.getBlockMetadata() % 4)
        {
            case 0:
                rotation = 270;
                break;
            case 1:
                rotation = 0;
                break;
            case 2:
                rotation = 90;
                break;
            case 3:
                rotation = 180;
                break;
        }

        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);

        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private void adjustLightFixture(World world, int i, int j, int k, Block block)
    {
        Tessellator tess = Tessellator.instance;
        float brightness = block.getLightOpacity(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
    }
}
