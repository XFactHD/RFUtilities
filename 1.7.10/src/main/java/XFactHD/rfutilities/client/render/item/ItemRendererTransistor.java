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

package XFactHD.rfutilities.client.render.item;

import XFactHD.rfutilities.client.models.ModelTransistor;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererTransistor implements IItemRenderer
{
    private ModelTransistor model = new ModelTransistor();

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/models/modelTransistorOff.png");
        switch (type)
        {
            case INVENTORY:
                GL11.glPushMatrix();
                GL11.glScalef(.11F, .11F, .11F);
                GL11.glRotatef(180F, 0F, 1F, 0F);
                GL11.glRotatef(180F, 0F, 0F, 1F);
                GL11.glTranslatef(0F, -6.7F, 0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render(null, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F);
                GL11.glPopMatrix();
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glPushMatrix();
                GL11.glScalef(.1F, .1F, .1F);
                GL11.glTranslatef(3F, 18F, 5F);
                GL11.glRotatef(180F, 0F, 1F, 0F);
                GL11.glRotatef(180F, 0F, 0F, 1F);
                GL11.glRotatef(90F, 0F, 1F, 0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render(null, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F);
                GL11.glPopMatrix();
                break;
            case EQUIPPED:
                GL11.glPushMatrix();
                GL11.glScalef(.2F, .2F, .2F);
                GL11.glTranslatef(2.4F, 15F, 2.4F);
                GL11.glRotatef(180F, 0F, 1F, 0F);
                GL11.glRotatef(180F, 0F, 0F, 1F);
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render(null, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F);
                GL11.glPopMatrix();
                break;
            case ENTITY:
                GL11.glPushMatrix();
                GL11.glScalef(.2F, .2F, .2F);
                GL11.glTranslatef(0F, 11F, 0F);
                GL11.glRotatef(180F, 0F, 1F, 0F);
                GL11.glRotatef(180F, 0F, 0F, 1F);
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render(null, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F);
                GL11.glPopMatrix();
                break;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }
}
