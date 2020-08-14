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
import XFactHD.rfutilities.common.blocks.tileEntity.TileEntityResistor;
import XFactHD.rfutilities.common.gui.ContainerResistor;
import XFactHD.rfutilities.common.utils.Reference;
import cofh.core.gui.GuiLimitedTextField;
import cpw.mods.fml.client.config.GuiCheckBox;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

public class GUIResistor extends GuiContainer
{
    public EntityPlayer player;
    public GuiTextField value;
    public TileEntityResistor tile;
    private boolean buttonAcceptEnabled = false;
    //private boolean transferMultiplePerTick = false;

    public GUIResistor(InventoryPlayer inv, TileEntityResistor te)
    {
        super(new ContainerResistor(inv, te));
        player = inv.player;
        this.tile = te;
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
        ClientUtils.bindTexture(Reference.GUI_FOLDER + "GUI_Resistor.png");
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 120, 114);
        value.drawTextBox();
        value.setText(value.getText());
        drawString(fontRendererObj, EnumChatFormatting.WHITE + "RF/t", guiLeft + 80, guiTop + 17, 16);
        //drawString(fontRendererObj, EnumChatFormatting.WHITE + "Allow multiple transfers per tick?", guiLeft + 20, guiTop + 36, 10);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        GuiButton accept = new GuiButton(0, guiLeft + 20, guiTop + 55, 80, 20, StatCollector.translateToLocal("guiDesc.rfutilities:accept.name"));
        GuiButton decline = new GuiButton(1, guiLeft + 20, guiTop + 83, 80, 20, StatCollector.translateToLocal("guiDesc.rfutilities:decline.name"));
        //GuiCheckBox transferControl = new GuiCheckBox(2, guiLeft + 15, guiTop + 36-36, "", true);
        value = new GuiLimitedTextField(this.fontRendererObj, guiLeft + 15, guiTop + 14, 90, 14, "0123456789");
        value.setCanLoseFocus(false);
        value.setFocused(true);
        value.setMaxStringLength(10);
        value.setText(Integer.toString(tile.getThroughput()));
        //transferControl.setIsChecked(!tile.getTransferOncePerTick());
        buttonAcceptEnabled = !Integer.toString(tile.getThroughput()).equals(value.getText())/* || (((GuiCheckBox)this.buttonList.get(2)).isChecked() != transferMultiplePerTick)*/;

        this.buttonList.add(accept);
        this.buttonList.add(decline);
        //this.buttonList.add(transferControl);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        value.updateCursorCounter();
        ((GuiButton)this.buttonList.get(0)).enabled = buttonAcceptEnabled;
    }

    private void updateButtonAccept()
    {
        buttonAcceptEnabled = (!("".equals(value.getText())) && !Integer.toString(tile.getThroughput()).equals(value.getText()))/* || (((GuiCheckBox)this.buttonList.get(2)).isChecked() != transferMultiplePerTick)*/;
    }

    @Override
    protected void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);
        if(!(par2 == Keyboard.KEY_E))
        {
            this.value.textboxKeyTyped(par1, par2);
            this.updateButtonAccept();
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1)
        {
            this.player.closeScreen();
        }
        else if (button.id == 0)
        {
            int throughput;
            if (value.getText().length() == 10 && Integer.parseInt(value.getText().substring(0, 8)) > 21474836)
            {
                throughput = Integer.MAX_VALUE;
                value.setText(Integer.toString(Integer.MAX_VALUE));
            }
            else
            {
                throughput = Integer.parseInt(value.getText());
            }
            tile.setThroughput(throughput);
            //transferMultiplePerTick = ((GuiCheckBox)this.buttonList.get(2)).isChecked();
            //tile.setTransferOncePerTick(!transferMultiplePerTick);
            this.updateButtonAccept();
        }
        //else if (button.id == 2)
        //{
        //    //LogHelper.info("Click!");
        //    updateButtonAccept();
        //}
    }

    @Override
    protected void mouseClicked(int x, int y, int key)
    {
        super.mouseClicked(x, y, key);
    }
}
