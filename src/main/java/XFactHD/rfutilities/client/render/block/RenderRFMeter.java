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

package XFactHD.rfutilities.client.render.block;


import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityRFMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderRFMeter extends TileEntitySpecialRenderer<TileEntityRFMeter>
{
    @Override
    public void renderTileEntityAt(TileEntityRFMeter te, double x, double y, double z, float scale, int destroyStage)
    {
        String lastRFDisp = te.lastRFDisp;
        String transferedRFDisp = te.transferedRFDisp;

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F); //TODO: check if offsets are necesary

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

        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);

        drawNumber(lastRFDisp,       1, 1, 1);
        drawNumber(transferedRFDisp, 1, 1, 1);

        GlStateManager.popMatrix();
    }

    private void drawNumber(String number, float x, float y, float z)
    {
        //TODO: implement
    }
}
