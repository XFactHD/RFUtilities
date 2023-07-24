package xfacthd.rfutilities.common.util.struct;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public record BlockEntityRO<T extends BlockEntity>(RegistryObject<BlockEntityType<T>> registryObject)
{
    public BlockEntityType<T> get() { return registryObject.get(); }
}
