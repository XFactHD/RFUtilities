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

public class ModelSwitch extends ModelBase
{
    //fields
    public ModelRenderer Bottom;
    public ModelRenderer ConnectorLeft;
    public ModelRenderer ConnectorRight;
    public ModelRenderer WireLeft;
    public ModelRenderer WireRight;
    public ModelRenderer SwitchCasing;
    public ModelRenderer LEDBack;
    public ModelRenderer LEDFront;
    public ModelRenderer LeverLeft;
    public ModelRenderer LeverRight;

    public ModelSwitch()
    {
        textureWidth = 64;
        textureHeight = 64;

        Bottom = new ModelRenderer(this, 0, 12);
        Bottom.addBox(0F, 0F, 0F, 16, 1, 16);
        Bottom.setRotationPoint(-8F, 23F, -8F);
        Bottom.setTextureSize(64, 64);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
        ConnectorLeft = new ModelRenderer(this, 36, 0);
        ConnectorLeft.addBox(0F, 0F, 0F, 1, 6, 6);
        ConnectorLeft.setRotationPoint(-8F, 17F, -3F);
        ConnectorLeft.setTextureSize(64, 64);
        ConnectorLeft.mirror = true;
        setRotation(ConnectorLeft, 0F, 0F, 0F);
        ConnectorRight = new ModelRenderer(this, 50, 0);
        ConnectorRight.addBox(0F, 0F, 0F, 1, 6, 6);
        ConnectorRight.setRotationPoint(7F, 17F, -3F);
        ConnectorRight.setTextureSize(64, 64);
        ConnectorRight.mirror = true;
        setRotation(ConnectorRight, 0F, 0F, 0F);
        WireLeft = new ModelRenderer(this, 0, 29);
        WireLeft.addBox(0F, 0F, 0F, 1, 2, 2);
        WireLeft.setRotationPoint(-7F, 19F, -1F);
        WireLeft.setTextureSize(64, 64);
        WireLeft.mirror = true;
        setRotation(WireLeft, 0F, 0F, 0F);
        WireRight = new ModelRenderer(this, 6, 29);
        WireRight.addBox(0F, 0F, 0F, 1, 2, 2);
        WireRight.setRotationPoint(6F, 19F, -1F);
        WireRight.setTextureSize(64, 64);
        WireRight.mirror = true;
        setRotation(WireRight, 0F, 0F, 0F);
        SwitchCasing = new ModelRenderer(this, 0, 0);
        SwitchCasing.addBox(0F, 0F, 0F, 12, 6, 6);
        SwitchCasing.setRotationPoint(-6F, 16F, -3F);
        SwitchCasing.setTextureSize(64, 64);
        SwitchCasing.mirror = true;
        setRotation(SwitchCasing, 0F, 0F, 0F);
        LEDBack = new ModelRenderer(this, 12, 29);
        LEDBack.addBox(0F, 0F, 0F, 2, 2, 1);
        LEDBack.setRotationPoint(-1F, 18F, 3F);
        LEDBack.setTextureSize(64, 64);
        LEDBack.mirror = true;
        setRotation(LEDBack, 0F, 0F, 0F);
        LEDFront = new ModelRenderer(this, 12, 29);
        LEDFront.addBox(0F, 0F, 0F, 2, 2, 1);
        LEDFront.setRotationPoint(-1F, 18F, -4F);
        LEDFront.setTextureSize(64, 64);
        LEDFront.mirror = true;
        setRotation(LEDFront, 0F, 0F, 0F);
        LeverLeft = new ModelRenderer(this, 18, 29);
        LeverLeft.addBox(-4F, 0F, 0F, 4, 3, 4);
        LeverLeft.setRotationPoint(0.5F, 15F, -2F);
        LeverLeft.setTextureSize(64, 64);
        LeverLeft.mirror = true;
        setRotation(LeverLeft, 0F, 0F, 0.2617994F);
        LeverRight = new ModelRenderer(this, 34, 29);
        LeverRight.addBox(0F, 0F, 0F, 4, 3, 4);
        LeverRight.setRotationPoint(0F, 15F, -2F);
        LeverRight.setTextureSize(64, 64);
        LeverRight.mirror = true;
        setRotation(LeverRight, 0F, 0F, -0.0174533F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Bottom.render(f5);
        ConnectorLeft.render(f5);
        ConnectorRight.render(f5);
        WireLeft.render(f5);
        WireRight.render(f5);
        SwitchCasing.render(f5);
        LEDBack.render(f5);
        LEDFront.render(f5);
        LeverLeft.render(f5);
        LeverRight.render(f5);
    }

    public void setRotation(ModelRenderer model, float x, float y, float z)
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
