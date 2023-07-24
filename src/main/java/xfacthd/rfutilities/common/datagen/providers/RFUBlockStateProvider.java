package xfacthd.rfutilities.common.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import xfacthd.rfutilities.RFUtilities;
import xfacthd.rfutilities.common.RFUContent;
import xfacthd.rfutilities.common.data.CapacitorType;

import java.util.function.Function;

public class RFUBlockStateProvider extends BlockStateProvider
{
    private final ResourceLocation capacitorTemplate = modLoc("block/capacitor_template");

    public RFUBlockStateProvider(DataGenerator gen, ExistingFileHelper fileHelper)
    {
        super(gen, RFUtilities.MOD_ID, fileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        for (CapacitorType type : CapacitorType.values())
        {
            Block block = RFUContent.getCapacitorBlock(type);
            ModelFile model = models().withExistingParent(type.getRegistryName(), capacitorTemplate)
                    .texture("type", modLoc("block/" + type.getRegistryName()));

            horizontalBlockWithItem(block, model);
        }

        horizontalBlockWithItem(
                RFUContent.BLOCK_ENERGY_METER.get(),
                models().getExistingFile(modLoc("block/energy_meter"))
        );

        registerSwitch();
    }

    private void registerSwitch()
    {
        Block block = RFUContent.BLOCK_SWITCH.get();
        ModelFile modelOff = models().getExistingFile(modLoc("block/switch_off"));
        ModelFile modelOn = models().getExistingFile(modLoc("block/switch_on"));

        horizontalBlockWithItem(
                block,
                s -> s.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff,
                modelOff
        );
    }

    private void horizontalBlockWithItem(Block block, Function<BlockState, ModelFile> blockModelFunc, ModelFile itemModel)
    {
        horizontalBlock(block, blockModelFunc, 0);
        simpleBlockItem(block, itemModel);
    }

    private void horizontalBlockWithItem(Block block, ModelFile model)
    {
        horizontalBlock(block, model, 0);
        simpleBlockItem(block, model);
    }
}
