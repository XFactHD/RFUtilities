package xfacthd.rfutilities.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import xfacthd.rfutilities.common.RFUContent;
import xfacthd.rfutilities.common.capability.ForwardingEnergyStorage;
import xfacthd.rfutilities.common.util.Utils;

public class SwitchBlockEntity extends BlockEntity
{
    private final SwitchedEnergyStorage storageCW;
    private final SwitchedEnergyStorage storageCCW;
    private LazyOptional<IEnergyStorage> lazyStorageCW = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyStorageCCW = LazyOptional.empty();
    private boolean active;

    public SwitchBlockEntity(BlockPos pos, BlockState state)
    {
        super(RFUContent.BLOCK_ENTITY_SWITCH.get(), pos, state);
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        this.storageCW = new SwitchedEnergyStorage(pos, facing.getCounterClockWise());
        this.storageCCW = new SwitchedEnergyStorage(pos, facing.getClockWise());
        this.active = state.getValue(BlockStateProperties.POWERED);
    }

    public void checkNeighborStorage(BlockPos neighbor)
    {
        if (neighbor == null || neighbor.equals(storageCW.targetPos))
        {
            storageCW.updateNeighbor();
        }
        if (neighbor == null || neighbor.equals(storageCCW.targetPos))
        {
            storageCCW.updateNeighbor();
        }
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side)
    {
        if (cap == ForgeCapabilities.ENERGY)
        {
            if (side == storageCW.targetSide.getOpposite())
            {
                return lazyStorageCW.cast();
            }
            else if (side == storageCCW.targetSide.getOpposite())
            {
                return lazyStorageCCW.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        lazyStorageCW = LazyOptional.of(() -> storageCW);
        lazyStorageCCW = LazyOptional.of(() -> storageCCW);
        checkNeighborStorage(null);
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        storageCW.clearNeighborRef();
        storageCCW.clearNeighborRef();
        lazyStorageCW.invalidate();
        lazyStorageCW = LazyOptional.empty();
        lazyStorageCCW.invalidate();
        lazyStorageCCW = LazyOptional.empty();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBlockState(BlockState state)
    {
        super.setBlockState(state);
        active = state.getValue(BlockStateProperties.POWERED);
        checkNeighborStorage(null);
    }



    private final class SwitchedEnergyStorage extends ForwardingEnergyStorage
    {
        private final BlockPos targetPos;
        private final Direction targetSide;
        private LazyOptional<IEnergyStorage> target = LazyOptional.empty();

        private SwitchedEnergyStorage(BlockPos pos, Direction targetSide)
        {
            this.targetPos = pos.relative(targetSide);
            this.targetSide = targetSide;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            return active ? super.receiveEnergy(maxReceive, simulate) : 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            return active ? super.extractEnergy(maxExtract, simulate) : 0;
        }

        @Override
        public boolean canReceive() { return active && super.canReceive(); }

        @Override
        public boolean canExtract() { return active && super.canExtract(); }

        @Override
        public LazyOptional<IEnergyStorage> getForwardTarget() { return target; }

        public void updateNeighbor()
        {
            //noinspection ConstantConditions
            target = Utils.tryGetNeighboringEnergyStorage(level, targetPos, targetSide, this::clearNeighborRef);
        }

        public void clearNeighborRef() { target = LazyOptional.empty(); }
    }
}
