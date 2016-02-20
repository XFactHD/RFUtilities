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

public class ModelResistorFloat extends ModelBase
{
  //fields
    ModelRenderer ConnectorLeft;
    ModelRenderer ConnectorRight;
    ModelRenderer WireLeft;
    ModelRenderer WireRight;
    ModelRenderer Resistor;
  
  public ModelResistorFloat()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      ConnectorLeft = new ModelRenderer(this, 36, 0);
      ConnectorLeft.addBox(0F, 0F, 0F, 1, 6, 6);
      ConnectorLeft.setRotationPoint(-8F, 13F, -3F);
      ConnectorLeft.setTextureSize(64, 32);
      ConnectorLeft.mirror = true;
      setRotation(ConnectorLeft, 0F, 0F, 0F);
      ConnectorRight = new ModelRenderer(this, 50, 0);
      ConnectorRight.addBox(0F, 0F, 0F, 1, 6, 6);
      ConnectorRight.setRotationPoint(7F, 13F, -3F);
      ConnectorRight.setTextureSize(64, 32);
      ConnectorRight.mirror = true;
      setRotation(ConnectorRight, 0F, 0F, 0F);
      WireLeft = new ModelRenderer(this, 0, 8);
      WireLeft.addBox(0F, 0F, 0F, 1, 2, 2);
      WireLeft.setRotationPoint(-7F, 15F, -1F);
      WireLeft.setTextureSize(64, 32);
      WireLeft.mirror = true;
      setRotation(WireLeft, 0F, 0F, 0F);
      WireRight = new ModelRenderer(this, 6, 8);
      WireRight.addBox(0F, 0F, 0F, 1, 2, 2);
      WireRight.setRotationPoint(6F, 15F, -1F);
      WireRight.setTextureSize(64, 32);
      WireRight.mirror = true;
      setRotation(WireRight, 0F, 0F, 0F);
      Resistor = new ModelRenderer(this, 0, 0);
      Resistor.addBox(0F, 0F, 0F, 12, 4, 4);
      Resistor.setRotationPoint(-6F, 14F, -2F);
      Resistor.setTextureSize(64, 32);
      Resistor.mirror = true;
      setRotation(Resistor, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    ConnectorLeft.render(f5);
    ConnectorRight.render(f5);
    WireLeft.render(f5);
    WireRight.render(f5);
    Resistor.render(f5);
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
