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

import XFactHD.rfutilities.client.models.ModelEnergyMeter;
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityRFMeter;
import XFactHD.rfutilities.common.utils.ConfigHandler;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderRFMeter extends TileEntitySpecialRenderer
{
    private ModelBase model;

    public RenderRFMeter()
    {
        this.model = ConfigHandler.floatingModels ? new ModelEnergyMeter() : new ModelEnergyMeter();
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
        final ResourceLocation textureLocation = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/models/modelEnergyMeter.png");
        int lastRFDisp = ((TileEntityRFMeter)te).lastRFDisp;
        String lastRFMark = ((TileEntityRFMeter)te).lastRFMark;
        int transferedRFDisp = ((TileEntityRFMeter)te).transferedRFDisp;
        String transferedRFMark = ((TileEntityRFMeter)te).transferedRFMark;

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        bindTexture(textureLocation);

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
        drawNumber(((long)lastRFDisp), 1, 1, 1, 1, 1, 1, false);

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private void drawNumber(long number, float x, float y, float z, float r, float g, float b, boolean dot)
    {

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