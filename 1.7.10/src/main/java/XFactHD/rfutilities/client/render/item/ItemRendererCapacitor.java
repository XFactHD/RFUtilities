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

package XFactHD.rfutilities.client.render.item;

import XFactHD.rfutilities.client.models.ModelCapacitorFloat;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererCapacitor implements IItemRenderer
{
    ModelCapacitorFloat model = new ModelCapacitorFloat();

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        int capType = item.hasTagCompound() ? item.stackTagCompound.getInteger("type") : 1;
        ResourceLocation texture = new ResourceLocation(Reference.MOD_ID + ":textures/models/modelCapacitor_" + capType + ".png");
        switch (type)
        {
            case INVENTORY:
                GL11.glPushMatrix();
                GL11.glRotatef(180F, 1F, 0F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.2F);
                GL11.glTranslatef(0F, -8F, 0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render(null, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F);
                GL11.glPopMatrix();
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glPushMatrix();
                GL11.glScalef(1.8F, 1.8F, 1.8F);
                GL11.glRotatef(-270F, 0F, 1F, 0F);
                GL11.glRotatef(180F, 0F, 0F, 1F);
                GL11.glTranslatef(0F, -5F, 20F);
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render(null, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F);
                GL11.glPopMatrix();
                break;
            case EQUIPPED:
                GL11.glPushMatrix();
                GL11.glRotatef(180F, 1F, 0F, 0F);
                GL11.glScalef(0.25F, 0.25F, 0.25F);
                GL11.glRotatef(180F, 0F, 1F, 0F);
                GL11.glRotatef(-45F, 0F, 1F, 0F);
                GL11.glTranslatef(0F, -7F, 3F);
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render(null, -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F);
                GL11.glPopMatrix();
                break;
            case ENTITY:
                GL11.glPushMatrix();
                GL11.glRotatef(180F, 1F, 0F, 0F);
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glTranslatef(0F, -12F, 0F);
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