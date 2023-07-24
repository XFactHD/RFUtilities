package xfacthd.rfutilities.common;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import xfacthd.rfutilities.RFUtilities;
import xfacthd.rfutilities.common.block.*;
import xfacthd.rfutilities.common.blockentity.*;
import xfacthd.rfutilities.common.data.CapacitorType;
import xfacthd.rfutilities.common.util.RFUCreativeTab;
import xfacthd.rfutilities.common.util.struct.BlockEntityRO;
import xfacthd.rfutilities.common.util.struct.CapacitorBlockEntities;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class RFUContent
{
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RFUtilities.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RFUtilities.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RFUtilities.MOD_ID);

    public static final Map<CapacitorType, RegistryObject<Block>> CAPACITOR_BLOCKS = registerCapacitors();
    public static final RegistryObject<Block> BLOCK_ENERGY_METER = registerBlock("energy_meter", EnergyMeterBlock::new);
    public static final RegistryObject<Block> BLOCK_SWITCH = registerBlock("switch", SwitchBlock::new);

    public static final CapacitorBlockEntities CAPACITOR_BLOCK_ENTITIES = registerCapacitorBlockEntities();
    public static final BlockEntityRO<EnergyMeterBlockEntity> BLOCK_ENTITY_ENERGY_METER = registerBlockEntity(
            "energy_meter", EnergyMeterBlockEntity::new, BLOCK_ENERGY_METER
    );
    public static final BlockEntityRO<SwitchBlockEntity> BLOCK_ENTITY_SWITCH = registerBlockEntity(
            "switch", SwitchBlockEntity::new, BLOCK_SWITCH
    );



    public static void init()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }

    public static Block getCapacitorBlock(CapacitorType type) { return CAPACITOR_BLOCKS.get(type).get(); }

    public static BlockEntityType<CapacitorBlockEntity> getCapacitorBlockEntity(CapacitorType type)
    {
        return CAPACITOR_BLOCK_ENTITIES.get(type).get();
    }



    private static Map<CapacitorType, RegistryObject<Block>> registerCapacitors()
    {
        ImmutableMap.Builder<CapacitorType, RegistryObject<Block>> capacitors = ImmutableMap.builder();
        for (CapacitorType type : CapacitorType.values())
        {
            RegistryObject<Block> block = registerBlock(type.getRegistryName(), () -> new CapacitorBlock(type));
            capacitors.put(type, block);
        }
        return capacitors.build();
    }

    private static CapacitorBlockEntities registerCapacitorBlockEntities()
    {
        ImmutableMap.Builder<CapacitorType, BlockEntityRO<CapacitorBlockEntity>> capacitors = ImmutableMap.builder();
        for (CapacitorType type : CapacitorType.values())
        {
            BlockEntityRO<CapacitorBlockEntity> blockEntity = registerBlockEntity(
                    type.getRegistryName(),
                    (pos, state) -> new CapacitorBlockEntity(pos, state, type),
                    CAPACITOR_BLOCKS.get(type)
            );
            capacitors.put(type, blockEntity);
        }
        return new CapacitorBlockEntities(capacitors.build());
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> factory)
    {
        RegistryObject<Block> block = BLOCKS.register(name, factory);
        registerItem(name, props -> new BlockItem(block.get(), props));
        return block;
    }

    private static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> factory)
    {
        return ITEMS.register(name, () -> factory.apply(defaultItemProperties()));
    }

    private static <T extends BlockEntity> BlockEntityRO<T> registerBlockEntity(
            String name, BlockEntityType.BlockEntitySupplier<T> factory, RegistryObject<Block> block
    )
    {
        return registerBlockEntity(name, factory, () -> new Block[] { block.get() });
    }

    private static <T extends BlockEntity> BlockEntityRO<T> registerBlockEntity(
            String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<Block[]> blocks
    )
    {
        return new BlockEntityRO<>(BLOCK_ENTITIES.register(name, () ->
        {
            //noinspection ConstantConditions
            return BlockEntityType.Builder.of(factory, blocks.get()).build(null);
        }));
    }

    private static Item.Properties defaultItemProperties()
    {
        return new Item.Properties().tab(RFUCreativeTab.INSTANCE);
    }
}
