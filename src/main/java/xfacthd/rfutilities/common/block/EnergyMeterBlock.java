package xfacthd.rfutilities.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import xfacthd.rfutilities.common.RFUContent;
import xfacthd.rfutilities.common.blockentity.EnergyMeterBlockEntity;
import xfacthd.rfutilities.common.util.Utils;

public class EnergyMeterBlock extends Block implements EntityBlock
{
    private static final VoxelShape[] SHAPES = makeShapes();

    public EnergyMeterBlock()
    {
        super(Properties.of(Material.METAL)
                .requiresCorrectToolForDrops()
                .strength(5F, 6F)
                .sound(SoundType.METAL)
        );
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (level.getBlockEntity(pos) instanceof EnergyMeterBlockEntity be)
        {
            if (!level.isClientSide())
            {
                if (player.isShiftKeyDown())
                {
                    be.clearAccumulated();
                }
                else
                {
                    be.toggleRecording();
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        return SHAPES[dir.get2DDataValue()];
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof EnergyMeterBlockEntity be)
        {
            be.checkNeighborStorage(fromPos);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new EnergyMeterBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> worldType)
    {
        return level.isClientSide() ? null : Utils.createTicker(worldType, RFUContent.BLOCK_ENTITY_ENERGY_METER.get(), EnergyMeterBlockEntity::tick);
    }



    private static VoxelShape[] makeShapes()
    {
        VoxelShape[] shapes = new VoxelShape[4];

        VoxelShape shape = Shapes.or(
                box(  0, 0,   0,   16,    1,   16), //Base
                box(3.5, 1, 5.5, 12.5,  2.5, 10.5), //Chip
                box(  3, 1, 1.5,    4,    4,  2.5), //Display wire
                box(  5, 1, 1.5,    6,    4,  2.5), //Display wire
                box(  8, 1, 1.5,    9,    4,  2.5), //Display wire
                box( 10, 1, 1.5,   11,    4,  2.5), //Display wire
                box( 12, 1, 1.5,   13,    4,  2.5), //Display wire
                box(  2, 4,   0,   14,   11,  3.5), //Display
                box(  0, 3,   5,    2,    9,   11), //Connector
                box( 14, 3,   5,   16,    9,   11), //Connector
                box(  0, 1,   7,    2,    3,    9), //Vertical wire
                box( 14, 1,   7,   16,    3,    9)  //Vertical wire
        );

        for (Direction dir : Direction.Plane.HORIZONTAL)
        {
            shapes[dir.get2DDataValue()] = Utils.rotateShape(Direction.NORTH, dir, shape);
        }

        return shapes;
    }
}
