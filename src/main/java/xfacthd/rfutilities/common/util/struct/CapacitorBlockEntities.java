package xfacthd.rfutilities.common.util.struct;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import xfacthd.rfutilities.common.blockentity.CapacitorBlockEntity;
import xfacthd.rfutilities.common.data.CapacitorType;

import java.util.Objects;

public record CapacitorBlockEntities(ImmutableMap<CapacitorType, BlockEntityRO<CapacitorBlockEntity>> map)
{
    public RegistryObject<BlockEntityType<CapacitorBlockEntity>> get(CapacitorType type)
    {
        return Objects.requireNonNull(map.get(type)).registryObject();
    }
}
