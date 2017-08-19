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

public class ModelEnergyMeter extends ModelBase
{
  //fields
    ModelRenderer Bottom;
    ModelRenderer ConnectorLeft;
    ModelRenderer ConnectorRight;
    ModelRenderer WireLeftHorizontal;
    ModelRenderer WireRightHorizontal;
    ModelRenderer WireLeftVertical;
    ModelRenderer WireRightVertical;
    ModelRenderer DisplayCorpus;
    ModelRenderer DisplayFrameRight;
    ModelRenderer DisplayFrameLeft;
    ModelRenderer DisplayFrameBottom;
    ModelRenderer DisplayFrameTop;
    ModelRenderer DisplayCon1;
    ModelRenderer DisplayCon2;
    ModelRenderer DisplayCon3;
    ModelRenderer DisplayCon4;
    ModelRenderer DisplayCon5;
    ModelRenderer DisplayWindow;
  
  public ModelEnergyMeter()
  {
    textureWidth = 128;
    textureHeight = 32;
    
      Bottom = new ModelRenderer(this, 0, 0);
      Bottom.addBox(0F, 0F, 0F, 16, 2, 16);
      Bottom.setRotationPoint(-8F, 22F, -8F);
      Bottom.setTextureSize(128, 32);
      Bottom.mirror = true;
      setRotation(Bottom, 0F, 0F, 0F);
      ConnectorLeft = new ModelRenderer(this, 0, 18);
      ConnectorLeft.addBox(0F, 0F, 0F, 2, 6, 6);
      ConnectorLeft.setRotationPoint(-8F, 13F, -3F);
      ConnectorLeft.setTextureSize(128, 32);
      ConnectorLeft.mirror = true;
      setRotation(ConnectorLeft, 0F, 0F, 0F);
      ConnectorRight = new ModelRenderer(this, 16, 18);
      ConnectorRight.addBox(0F, 0F, 0F, 2, 6, 6);
      ConnectorRight.setRotationPoint(6F, 13F, -3F);
      ConnectorRight.setTextureSize(128, 32);
      ConnectorRight.mirror = true;
      setRotation(ConnectorRight, 0F, 0F, 0F);
      WireLeftHorizontal = new ModelRenderer(this, 32, 18);
      WireLeftHorizontal.addBox(0F, 0F, 0F, 2, 2, 2);
      WireLeftHorizontal.setRotationPoint(-6F, 15F, -1F);
      WireLeftHorizontal.setTextureSize(128, 32);
      WireLeftHorizontal.mirror = true;
      setRotation(WireLeftHorizontal, 0F, 0F, 0F);
      WireRightHorizontal = new ModelRenderer(this, 32, 22);
      WireRightHorizontal.addBox(0F, 0F, 0F, 2, 2, 2);
      WireRightHorizontal.setRotationPoint(4F, 15F, -1F);
      WireRightHorizontal.setTextureSize(128, 32);
      WireRightHorizontal.mirror = true;
      setRotation(WireRightHorizontal, 0F, 0F, 0F);
      WireLeftVertical = new ModelRenderer(this, 40, 18);
      WireLeftVertical.addBox(0F, 0F, 0F, 2, 7, 2);
      WireLeftVertical.setRotationPoint(-4F, 15F, -1F);
      WireLeftVertical.setTextureSize(128, 32);
      WireLeftVertical.mirror = true;
      setRotation(WireLeftVertical, 0F, 0F, 0F);
      WireRightVertical = new ModelRenderer(this, 48, 18);
      WireRightVertical.addBox(0F, 0F, 0F, 2, 7, 2);
      WireRightVertical.setRotationPoint(2F, 15F, -1F);
      WireRightVertical.setTextureSize(128, 32);
      WireRightVertical.mirror = true;
      setRotation(WireRightVertical, 0F, 0F, 0F);
      DisplayCorpus = new ModelRenderer(this, 64, 0);
      DisplayCorpus.addBox(0F, 0F, 0F, 12, 7, 3);
      DisplayCorpus.setRotationPoint(-6F, 12F, -7F);
      DisplayCorpus.setTextureSize(128, 32);
      DisplayCorpus.mirror = true;
      setRotation(DisplayCorpus, 0F, 0F, 0F);
      DisplayFrameRight = new ModelRenderer(this, 64, 10);
      DisplayFrameRight.addBox(0F, 0F, 0F, 1, 7, 1);
      DisplayFrameRight.setRotationPoint(-6F, 12F, -7.5F);
      DisplayFrameRight.setTextureSize(128, 32);
      DisplayFrameRight.mirror = true;
      setRotation(DisplayFrameRight, 0F, 0F, 0F);
      DisplayFrameLeft = new ModelRenderer(this, 68, 10);
      DisplayFrameLeft.addBox(0F, 0F, 0F, 1, 7, 1);
      DisplayFrameLeft.setRotationPoint(5F, 12F, -7.5F);
      DisplayFrameLeft.setTextureSize(128, 32);
      DisplayFrameLeft.mirror = true;
      setRotation(DisplayFrameLeft, 0F, 0F, 0F);
      DisplayFrameBottom = new ModelRenderer(this, 72, 10);
      DisplayFrameBottom.addBox(0F, 0F, 0F, 10, 1, 1);
      DisplayFrameBottom.setRotationPoint(-5F, 18F, -7.5F);
      DisplayFrameBottom.setTextureSize(128, 32);
      DisplayFrameBottom.mirror = true;
      setRotation(DisplayFrameBottom, 0F, 0F, 0F);
      DisplayFrameTop = new ModelRenderer(this, 72, 12);
      DisplayFrameTop.addBox(0F, 0F, 0F, 10, 1, 1);
      DisplayFrameTop.setRotationPoint(-5F, 12F, -7.5F);
      DisplayFrameTop.setTextureSize(128, 32);
      DisplayFrameTop.mirror = true;
      setRotation(DisplayFrameTop, 0F, 0F, 0F);
      DisplayCon1 = new ModelRenderer(this, 32, 26);
      DisplayCon1.addBox(0F, 0F, 0F, 1, 3, 1);
      DisplayCon1.setRotationPoint(-5F, 19F, -6F);
      DisplayCon1.setTextureSize(128, 32);
      DisplayCon1.mirror = true;
      setRotation(DisplayCon1, 0F, 0F, 0F);
      DisplayCon2 = new ModelRenderer(this, 32, 26);
      DisplayCon2.addBox(0F, 0F, 0F, 1, 3, 1);
      DisplayCon2.setRotationPoint(-3F, 19F, -6F);
      DisplayCon2.setTextureSize(128, 32);
      DisplayCon2.mirror = true;
      setRotation(DisplayCon2, 0F, 0F, 0F);
      DisplayCon3 = new ModelRenderer(this, 32, 26);
      DisplayCon3.addBox(0F, 0F, 0F, 1, 3, 1);
      DisplayCon3.setRotationPoint(4F, 19F, -6F);
      DisplayCon3.setTextureSize(128, 32);
      DisplayCon3.mirror = true;
      setRotation(DisplayCon3, 0F, 0F, 0F);
      DisplayCon4 = new ModelRenderer(this, 32, 26);
      DisplayCon4.addBox(0F, 0F, 0F, 1, 3, 1);
      DisplayCon4.setRotationPoint(2F, 19F, -6F);
      DisplayCon4.setTextureSize(128, 32);
      DisplayCon4.mirror = true;
      setRotation(DisplayCon4, 0F, 0F, 0F);
      DisplayCon5 = new ModelRenderer(this, 32, 26);
      DisplayCon5.addBox(0F, 0F, 0F, 1, 3, 1);
      DisplayCon5.setRotationPoint(-1F, 19F, -6F);
      DisplayCon5.setTextureSize(128, 32);
      DisplayCon5.mirror = true;
      setRotation(DisplayCon5, 0F, 0F, 0F);
      DisplayWindow = new ModelRenderer(this, 73, 15);
      DisplayWindow.addBox(0F, 0F, 0F, 10, 5, 0);
      DisplayWindow.setRotationPoint(-5F, 13F, -7.2F);
      DisplayWindow.setTextureSize(128, 32);
      DisplayWindow.mirror = true;
      setRotation(DisplayWindow, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Bottom.render(f5);
    ConnectorLeft.render(f5);
    ConnectorRight.render(f5);
    WireLeftHorizontal.render(f5);
    WireRightHorizontal.render(f5);
    WireLeftVertical.render(f5);
    WireRightVertical.render(f5);
    DisplayCorpus.render(f5);
    DisplayFrameRight.render(f5);
    DisplayFrameLeft.render(f5);
    DisplayFrameBottom.render(f5);
    DisplayFrameTop.render(f5);
    DisplayCon1.render(f5);
    DisplayCon2.render(f5);
    DisplayCon3.render(f5);
    DisplayCon4.render(f5);
    DisplayCon5.render(f5);
    DisplayWindow.render(f5);
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
