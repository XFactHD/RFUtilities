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

package XFactHD.rfutilities.client.gui;

import XFactHD.rfutilities.client.utils.ClientUtils;
import XFactHD.rfutilities.common.gui.ContainerInvisTess;
import XFactHD.rfutilities.common.gui.ContainerResistor;
import XFactHD.rfutilities.common.utils.Reference;
import XFactHD.rfutilities.common.utils.Utils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GUIResistor extends GuiContainer
{
    public int sH = Utils.screenHeight();
    public int sW = Utils.screenWidth();
    public int texXSize = 176;
    public int texYSize = 166;

    public GUIResistor(InventoryPlayer inv, TileEntity te)
    {
        super(new ContainerResistor(inv, te));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partial)
    {
        super.drawScreen(mouseX, mouseY, partial);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientUtils.bindTexture(Reference.GUI_FOLDER + "guiResistor.png");
        this.drawTexturedModalRect(guiLeft, guiTop + 35, 0, 0, texXSize, texYSize);
        ClientUtils.bindTexture(Reference.GUI_FOLDER + "guiResistor.png");
    }

    @Override
    protected void drawHoveringText(List strings, int x, int y, FontRenderer font)
    {

    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {

    }
}
