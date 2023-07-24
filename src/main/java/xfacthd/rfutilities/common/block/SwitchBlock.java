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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import xfacthd.rfutilities.common.blockentity.CapacitorBlockEntity;
import xfacthd.rfutilities.common.blockentity.SwitchBlockEntity;
import xfacthd.rfutilities.common.util.Utils;

public class SwitchBlock extends Block implements EntityBlock
{
    private static final VoxelShape SHAPE_Z = makeShape();
    private static final VoxelShape SHAPE_X = Utils.rotateShape(Direction.NORTH, Direction.EAST, SHAPE_Z);

    public SwitchBlock()
    {
        super(Properties.of(Material.METAL)
                .requiresCorrectToolForDrops()
                .strength(5F, 6F)
                .sound(SoundType.METAL)
        );
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.POWERED);
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
        if (!level.isClientSide())
        {
            boolean powered = state.getValue(BlockStateProperties.POWERED);
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.POWERED, !powered));
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
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
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new SwitchBlockEntity(pos, state); }



    private static VoxelShape makeShape()
    {
        return Shapes.or(
                box( 0, 0, 0, 16,  1, 16), //Base
                box( 2, 2, 5, 14,  8, 11), //Body
                box( 4, 8, 6, 12, 10, 10), //Rocker
                box( 0, 1, 5,  1,  7, 11), //Connector
                box(15, 1, 5, 16,  7, 11), //Connector
                box( 1, 3, 7,  2,  5,  9), //Horizontal wire
                box(14, 3, 7, 15,  5,  9)  //Horizontal wire
        );
    }
}
