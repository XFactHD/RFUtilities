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

public class ModelCapacitorFloat extends ModelBase
{
  //fields
    ModelRenderer ConnectorLeft;
    ModelRenderer ConnectorRight;
    ModelRenderer WireVerticalLeft;
    ModelRenderer WireVerticalRight;
    ModelRenderer Capacitor;
  
  public ModelCapacitorFloat()
  {
    textureWidth = 128;
    textureHeight = 32;
    
      ConnectorLeft = new ModelRenderer(this, 0, 20);
      ConnectorLeft.addBox(0F, 0F, 0F, 1, 6, 6);
      ConnectorLeft.setRotationPoint(-8F, 13F, -3F);
      ConnectorLeft.setTextureSize(128, 32);
      ConnectorLeft.mirror = true;
      setRotation(ConnectorLeft, 0F, 0F, 0F);
      ConnectorRight = new ModelRenderer(this, 14, 20);
      ConnectorRight.addBox(0F, 0F, 0F, 1, 6, 6);
      ConnectorRight.setRotationPoint(7F, 13F, -3F);
      ConnectorRight.setTextureSize(128, 32);
      ConnectorRight.mirror = true;
      setRotation(ConnectorRight, 0F, 0F, 0F);
      WireVerticalLeft = new ModelRenderer(this, 24, 0);
      WireVerticalLeft.addBox(0F, 0F, 0F, 6, 2, 2);
      WireVerticalLeft.setRotationPoint(-7F, 15F, -1F);
      WireVerticalLeft.setTextureSize(128, 32);
      WireVerticalLeft.mirror = true;
      setRotation(WireVerticalLeft, 0F, 0F, 0F);
      WireVerticalRight = new ModelRenderer(this, 24, 0);
      WireVerticalRight.addBox(0F, 0F, 0F, 6, 2, 2);
      WireVerticalRight.setRotationPoint(1F, 15F, -1F);
      WireVerticalRight.setTextureSize(128, 32);
      WireVerticalRight.mirror = true;
      setRotation(WireVerticalRight, 0F, 0F, 0F);
      WireVerticalRight.mirror = false;
      Capacitor = new ModelRenderer(this, 0, 0);
      Capacitor.addBox(0F, 0F, 0F, 8, 12, 8);
      Capacitor.setRotationPoint(-4F, 10F, -4F);
      Capacitor.setTextureSize(128, 32);
      Capacitor.mirror = true;
      setRotation(Capacitor, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    ConnectorLeft.render(f5);
    ConnectorRight.render(f5);
    WireVerticalLeft.render(f5);
    WireVerticalRight.render(f5);
    Capacitor.render(f5);
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
