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

package XFactHD.rfutilities.client.utils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiFilteredTextField extends GuiTextField {
    private String listOfValidCharacters = "";

    public GuiFilteredTextField(int id, FontRenderer fontRenderer, int x, int y, int width, int height, String filter)
    {
        super(id, fontRenderer, x, y, width, height);
        this.listOfValidCharacters = filter;
    }

    @Override
    public boolean textboxKeyTyped(char key, int buttonID)
    {
        switch(key)
        {
            case '\u0001': return super.textboxKeyTyped(key, buttonID);
            case '\u0003': return super.textboxKeyTyped(key, buttonID);
            case '\u0016': return false;
            case '\u0018': return super.textboxKeyTyped(key, buttonID);
            default:
                switch(buttonID)
                {
                    case 14:  return super.textboxKeyTyped(key, buttonID);
                    case 199: return super.textboxKeyTyped(key, buttonID);
                    case 203: return super.textboxKeyTyped(key, buttonID);
                    case 205: return super.textboxKeyTyped(key, buttonID);
                    case 207: return super.textboxKeyTyped(key, buttonID);
                    case 211: return super.textboxKeyTyped(key, buttonID);
                    default: return this.listOfValidCharacters.indexOf(key) >= 0?super.textboxKeyTyped(key, buttonID):false;
                }
        }
    }
}