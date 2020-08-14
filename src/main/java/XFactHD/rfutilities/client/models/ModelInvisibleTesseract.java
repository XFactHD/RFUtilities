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

package XFactHD.rfutilities.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelInvisibleTesseract extends ModelBase
{
    //fields
    ModelRenderer Backplane;
    ModelRenderer FrontLeft;
    ModelRenderer FrontRight;
    ModelRenderer FrontTop;
    ModelRenderer FrontBottom;
    ModelRenderer FrontInner;
    ModelRenderer IndicatorRec;
    ModelRenderer IndicatorSend;
  
    public ModelInvisibleTesseract()
    {
        textureWidth = 64;
        textureHeight = 32;

        Backplane = new ModelRenderer(this, 0, 0);
        Backplane.addBox(0F, 0F, 0F, 12, 12, 1);
        Backplane.setRotationPoint(-6F, 10F, 7F);
        Backplane.setTextureSize(textureWidth, textureHeight);
        Backplane.mirror = true;
        setRotation(Backplane, 0F, 0F, 0F);
        FrontLeft = new ModelRenderer(this, 26, 0);
        FrontLeft.addBox(0F, 0F, 0F, 2, 12, 2);
        FrontLeft.setRotationPoint(-6F, 10F, 5F);
        FrontLeft.setTextureSize(textureWidth, textureHeight);
        FrontLeft.mirror = true;
        setRotation(FrontLeft, 0F, 0F, 0F);
        FrontRight = new ModelRenderer(this, 34, 0);
        FrontRight.addBox(0F, 0F, 0F, 2, 12, 2);
        FrontRight.setRotationPoint(4F, 10F, 5F);
        FrontRight.setTextureSize(textureWidth, textureHeight);
        FrontRight.mirror = true;
        setRotation(FrontRight, 0F, 0F, 0F);
        FrontTop = new ModelRenderer(this, 0, 13);
        FrontTop.addBox(0F, 0F, 0F, 8, 2, 2);
        FrontTop.setRotationPoint(-4F, 10F, 5F);
        FrontTop.setTextureSize(textureWidth, textureHeight);
        FrontTop.mirror = true;
        setRotation(FrontTop, 0F, 0F, 0F);
        FrontBottom = new ModelRenderer(this, 0, 17);
        FrontBottom.addBox(0F, 0F, 0F, 8, 2, 2);
        FrontBottom.setRotationPoint(-4F, 20F, 5F);
        FrontBottom.setTextureSize(textureWidth, textureHeight);
        FrontBottom.mirror = true;
        setRotation(FrontBottom, 0F, 0F, 0F);
        FrontInner = new ModelRenderer(this, 0, 21);
        FrontInner.addBox(0F, 0F, 0F, 8, 8, 1);
        FrontInner.setRotationPoint(-4F, 12F, 6F);
        FrontInner.setTextureSize(textureWidth, textureHeight);
        FrontInner.mirror = true;
        setRotation(FrontInner, 0F, 0F, 0F);
        IndicatorRec = new ModelRenderer(this, 41, 1);
        IndicatorRec.addBox(0F, 0F, 0F, 12, 12, 0);
        IndicatorRec.setRotationPoint(-6, 10, 5);
        IndicatorRec.offsetZ = -0.001F;
        IndicatorRec.setTextureSize(textureWidth, textureHeight);
        IndicatorRec.mirror = true;
        setRotation(IndicatorRec, 0F, 0F, 0F);
        IndicatorSend = new ModelRenderer(this, 41, 15);
        IndicatorSend.addBox(0F, 0F, 0F, 12, 12, 0);
        IndicatorSend.setRotationPoint(-6, 10, 5);
        IndicatorSend.offsetZ = -0.001F;
        IndicatorSend.setTextureSize(textureWidth, textureHeight);
        IndicatorSend.mirror = true;
        setRotation(IndicatorSend, 0F, 0F, 0F);
    }
  
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean inventory, boolean sender)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Backplane.render(f5);
        FrontLeft.render(f5);
        FrontRight.render(f5);
        FrontTop.render(f5);
        FrontBottom.render(f5);
        FrontInner.render(f5);
        if (!inventory)
        {
            if (sender)
            {
                IndicatorSend.render(f5);
            }
            else
            {
                IndicatorRec.render(f5);
            }
        }
    }
  
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
  
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }

}
