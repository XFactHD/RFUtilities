package xfacthd.rfutilities.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.*;
import xfacthd.rfutilities.common.RFUContent;
import xfacthd.rfutilities.common.blockentity.CapacitorBlockEntity;
import xfacthd.rfutilities.common.data.CapacitorType;
import xfacthd.rfutilities.common.util.Utils;

public class CapacitorBlock extends Block implements EntityBlock
{
    private static final VoxelShape SHAPE_Z = makeShape();
    private static final VoxelShape SHAPE_X = Utils.rotateShape(Direction.NORTH, Direction.EAST, SHAPE_Z);

    private final CapacitorType type;

    public CapacitorBlock(CapacitorType type)
    {
        super(Properties.of(Material.METAL)
                .requiresCorrectToolForDrops()
                .strength(5F, 6F)
                .sound(SoundType.METAL)
        );
        this.type = type;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(
                BlockStateProperties.HORIZONTAL_FACING,
                context.getHorizontalDirection().getOpposite()
        );
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        return Utils.isX(dir) ? SHAPE_X : SHAPE_Z;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof CapacitorBlockEntity be)
        {
            be.checkNeighborStorage(fromPos);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new CapacitorBlockEntity(pos, state, type);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> worldType)
    {
        if (!level.isClientSide())
        {
            return Utils.createTicker(worldType, RFUContent.getCapacitorBlockEntity(type), CapacitorBlockEntity::tick);
        }
        return null;
    }



    private static VoxelShape makeShape()
    {
        return Shapes.or(
                box( 0, 0, 0, 16,  1, 16), //Base
                box( 4, 6, 4, 12, 18, 12), //Body
                box( 0, 1, 5,  1,  7, 11), //Connector
                box(15, 1, 5, 16,  7, 11), //Connector
                box( 1, 3, 7,  7,  5,  9), //Horizontal wire
                box( 9, 3, 7, 15,  5,  9), //Horizontal wire
                box( 5, 5, 7,  7,  6,  9), //Vertical wire
                box( 9, 5, 7, 11,  6,  9)  //Vertical wire
        );
    }
}
