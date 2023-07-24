package xfacthd.rfutilities.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.function.Consumer;

public class Utils
{
    @SuppressWarnings("unchecked")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTicker(
            BlockEntityType<A> worldType, BlockEntityType<E> actualType, BlockEntityTicker<? super E> ticker
    )
    {
        return actualType == worldType ? (BlockEntityTicker<A>)ticker : null;
    }

    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTicker(
            BlockEntityType<A> worldType, BlockEntityType<E> actualType, Consumer<? super E> ticker
    )
    {
        return createTicker(worldType, actualType, (level, pos, state, be) -> ticker.accept(be));
    }

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape)
    {
        if (isY(from) || isY(to)) { throw new IllegalArgumentException("Invalid Direction!"); }
        if (from == to) { return shape; }

        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };

        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++)
        {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(
                    buffer[1],
                    Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)
            ));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public static boolean isPositive(Direction dir) { return dir.getAxisDirection() == Direction.AxisDirection.POSITIVE; }

    public static boolean isX(Direction dir) { return dir.getAxis() == Direction.Axis.X; }

    public static boolean isY(Direction dir) { return dir.getAxis() == Direction.Axis.Y; }

    public static boolean isZ(Direction dir) { return dir.getAxis() == Direction.Axis.Z; }

    public static LazyOptional<IEnergyStorage> tryGetNeighboringEnergyStorage(Level level, BlockPos pos, Direction side, Runnable invalidationListener)
    {
        if (level.isLoaded(pos) && !level.isEmptyBlock(pos))
        {
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null)
            {
                LazyOptional<IEnergyStorage> storage = be.getCapability(ForgeCapabilities.ENERGY, side.getOpposite());
                if (storage.isPresent())
                {
                    storage.addListener($ -> invalidationListener.run());
                }
                return storage;
            }
            else
            {
                return LazyOptional.empty();
            }
        }
        else
        {
            return LazyOptional.empty();
        }
    }
}
