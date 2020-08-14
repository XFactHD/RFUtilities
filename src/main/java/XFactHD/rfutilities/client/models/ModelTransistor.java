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

package XFactHD.rfutilities.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTransistor extends ModelBase
{

    private ModelRenderer Bottom;
    private ModelRenderer ConnectorLeft;
    private ModelRenderer ConnectorRight;
    private ModelRenderer WireLeft;
    private ModelRenderer WireRight;
    private ModelRenderer WireMiddle;
    private ModelRenderer WireLeftVertical;
    private ModelRenderer WireRightVertical;
    private ModelRenderer WireMiddleVertical;
    private ModelRenderer Transistor;
    private ModelRenderer Cooler;
    private ModelRenderer HoleTop;
    private ModelRenderer HoleBottom;
    private ModelRenderer HoleLeft;
    private ModelRenderer HoleRight;
  
    public ModelTransistor()
    {
        textureWidth = 128;
        textureHeight = 32;
    
        Bottom = new ModelRenderer(this, 32, 15);
        Bottom.addBox(0F, 0F, 0F, 16, 1, 16);
        Bottom.setRotationPoint(-8F, 23F, -8F);
        Bottom.setTextureSize(128, 32);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
        ConnectorLeft = new ModelRenderer(this, 32, 0);
        ConnectorLeft.addBox(0F, 0F, 0F, 1, 6, 6);
        ConnectorLeft.setRotationPoint(-8F, 17F, -3F);
        ConnectorLeft.setTextureSize(128, 32);
        ConnectorLeft.mirror = true;
        setRotation(ConnectorLeft, 0F, 0F, 0F);
        ConnectorRight = new ModelRenderer(this, 46, 0);
        ConnectorRight.addBox(0F, 0F, 0F, 1, 6, 6);
        ConnectorRight.setRotationPoint(7F, 17F, -3F);
        ConnectorRight.setTextureSize(128, 32);
        ConnectorRight.mirror = true;
        setRotation(ConnectorRight, 0F, 0F, 0F);
        WireLeft = new ModelRenderer(this, 32, 27);
        WireLeft.addBox(0F, 0F, 0F, 5, 2, 2);
        WireLeft.setRotationPoint(-7F, 19F, -1F);
        WireLeft.setTextureSize(128, 32);
        WireLeft.mirror = true;
        setRotation(WireLeft, 0F, 0F, 0F);
        WireRight = new ModelRenderer(this, 32, 27);
        WireRight.addBox(0F, 0F, 0F, 5, 2, 2);
        WireRight.setRotationPoint(2F, 19F, -1F);
        WireRight.setTextureSize(128, 32);
        WireRight.mirror = true;
        setRotation(WireRight, 0F, 0F, 0F);
        WireMiddle = new ModelRenderer(this, 80, 23);
        WireMiddle.addBox(0F, 0F, 0F, 2, 1, 7);
        WireMiddle.setRotationPoint(-1F, 22F, 1F);
        WireMiddle.setTextureSize(128, 32);
        WireMiddle.mirror = true;
        setRotation(WireMiddle, 0F, 0F, 0F);
        WireLeftVertical = new ModelRenderer(this, 32, 27);
        WireLeftVertical.addBox(0F, 0F, 0F, 2, 1, 2);
        WireLeftVertical.setRotationPoint(-4F, 18F, -1F);
        WireLeftVertical.setTextureSize(128, 32);
        WireLeftVertical.mirror = true;
        setRotation(WireLeftVertical, 0F, 0F, 0F);
        WireRightVertical = new ModelRenderer(this, 32, 27);
        WireRightVertical.addBox(0F, 0F, 0F, 2, 1, 2);
        WireRightVertical.setRotationPoint(2F, 18F, -1F);
        WireRightVertical.setTextureSize(128, 32);
        WireRightVertical.mirror = true;
        setRotation(WireRightVertical, 0F, 0F, 0F);
        WireMiddleVertical = new ModelRenderer(this, 32, 15);
        WireMiddleVertical.addBox(0F, 0F, 0F, 2, 5, 2);
        WireMiddleVertical.setRotationPoint(-1F, 18F, -1F);
        WireMiddleVertical.setTextureSize(128, 32);
        WireMiddleVertical.mirror = true;
        setRotation(WireMiddleVertical, 0F, 0F, 0F);
        Transistor = new ModelRenderer(this, 0, 0);
        Transistor.addBox(0F, 0F, 0F, 10, 10, 6);
        Transistor.setRotationPoint(-5F, 8F, -2F);
        Transistor.setTextureSize(128, 32);
        Transistor.mirror = true;
        setRotation(Transistor, 0F, 0F, 0F);
        Cooler = new ModelRenderer(this, 0, 16);
        Cooler.addBox(0F, 0F, 0F, 10, 7, 1);
        Cooler.setRotationPoint(-5F, 1F, 3F);
        Cooler.setTextureSize(128, 32);
        Cooler.mirror = true;
        setRotation(Cooler, 0F, 0F, 0F);
        HoleTop = new ModelRenderer(this, 22, 16);
        HoleTop.addBox(0F, 0F, 0F, 2, 1, 1);
        HoleTop.setRotationPoint(-1F, 2F, 3F);
        HoleTop.setTextureSize(128, 32);
        HoleTop.mirror = true;
        setRotation(HoleTop, 0F, 0F, 0F);
        HoleBottom = new ModelRenderer(this, 22, 18);
        HoleBottom.addBox(0F, 0F, 0F, 2, 1, 1);
        HoleBottom.setRotationPoint(-1F, 5F, 3F);
        HoleBottom.setTextureSize(128, 32);
        HoleBottom.mirror = true;
        setRotation(HoleBottom, 0F, 0F, 0F);
        HoleLeft = new ModelRenderer(this, 22, 20);
        HoleLeft.addBox(0F, 0F, 0F, 1, 2, 1);
        HoleLeft.setRotationPoint(-2F, 3F, 3F);
        HoleLeft.setTextureSize(128, 32);
        HoleLeft.mirror = true;
        setRotation(HoleLeft, 0F, 0F, 0F);
        HoleRight = new ModelRenderer(this, 26, 20);
        HoleRight.addBox(0F, 0F, 0F, 1, 2, 1);
        HoleRight.setRotationPoint(1F, 3F, 3F);
        HoleRight.setTextureSize(128, 32);
        HoleRight.mirror = true;
        setRotation(HoleRight, 0F, 0F, 0F);
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
        WireMiddle.render(f5);
        WireLeftVertical.render(f5);
        WireRightVertical.render(f5);
        WireMiddleVertical.render(f5);
        Transistor.render(f5);
        Cooler.render(f5);
        HoleTop.render(f5);
        HoleBottom.render(f5);
        HoleLeft.render(f5);
        HoleRight.render(f5);
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
