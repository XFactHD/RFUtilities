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

import XFactHD.rfutilities.client.models.SmartModelCapacitor;
import XFactHD.rfutilities.common.utils.Reference;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ClientEventHandler
{
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void modelBake(ModelBakeEvent event)
    {
        List<IBakedModel> capacitors = new ArrayList<IBakedModel>();

        IBakedModel teBasic      = event.modelRegistry.getObject(new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "facing=north,type=1"));
        IBakedModel teHardened   = event.modelRegistry.getObject(new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "facing=north,type=2"));
        IBakedModel teReinforced = event.modelRegistry.getObject(new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "facing=north,type=3"));
        IBakedModel teResonant   = event.modelRegistry.getObject(new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "facing=north,type=4"));
        IBakedModel eioBasic     = event.modelRegistry.getObject(new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "facing=north,type=5"));
        IBakedModel eioDouble    = event.modelRegistry.getObject(new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "facing=north,type=6"));
        IBakedModel eioVibrant   = event.modelRegistry.getObject(new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "facing=north,type=7"));

        capacitors.add(teBasic);
        capacitors.add(teHardened);
        capacitors.add(teReinforced);
        capacitors.add(teResonant);
        capacitors.add(eioBasic);
        capacitors.add(eioDouble);
        capacitors.add(eioVibrant);

        SmartModelCapacitor capacitorModel = new SmartModelCapacitor(capacitors);
        event.modelRegistry.putObject(new ModelResourceLocation(Reference.MOD_ID + ":blockCapacitor", "inventory"), capacitorModel);
    }
}
